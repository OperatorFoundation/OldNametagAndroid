package org.operatorfoundation.nametagandroid

import android.provider.ContactsContract.Data
import org.operatorfoundation.keychainandroid.KeyType
import org.operatorfoundation.keychainandroid.Keychain
import org.operatorfoundation.keychainandroid.PublicKey
import org.operatorfoundation.keychainandroid.Signature
import org.operatorfoundation.keychainandroid.SignatureType
import org.operatorfoundation.keychainandroid.PrivateKey
import org.operatorfoundation.transmission.Connection
import org.operatorfoundation.transmission.TransmissionConnection
import kotlin.random.Random

class Nametag {
    val challengeSize: Int = 64
    val expectedPublicKeySize: Int = 65
    val expectedSignatureSize: Int = 64

    fun check(challenge: ByteArray, clientPublicKey: PublicKey, signature: Signature)
    {
        if (!clientPublicKey.isValidSignature(signature, challenge))
        {
            throw Exception(NametagError.verificationFailed.toString())
        }
    }

    fun checkLive(connection: TransmissionConnection): PublicKey
    {
        val clientPublicKeyData = connection.read(expectedPublicKeySize) ?: throw Exception(NametagError.noPublicKeyReceived.toString())
        val clientPublicKey = PublicKey.new(clientPublicKeyData)
        val challenge = Random.nextBytes(challengeSize)
        val wroteChallenge = connection.write(challenge)

        if (!wroteChallenge)
        {
            throw Exception(NametagError.writeFailed.toString())
        }

        val signatureData = connection.read(expectedSignatureSize) ?: throw Exception(NametagError.noSignatureReceived.toString())

        // FIXME: Did I do this correctly?
        val signature = Signature.P256(signatureData)

        try {
            check(challenge, clientPublicKey, signature)
        }
        catch (error: Error)
        {
            throw error
        }

        return clientPublicKey
    }

    // TODO: Use KeychainLibrary
    lateinit var privateKey: PrivateKey
    lateinit var publicKey:  PublicKey

    var data = publicKey.data

    init {
        val keychain = Keychain()
        val privateSigningKey = keychain.retrieveOrGeneratePrivateKey("Nametag", KeyType.P256Signing)
        if (privateSigningKey != null) {
            this.privateKey = privateSigningKey
            this.publicKey = privateSigningKey.
        }
    }

    fun prove(challenge: ByteArray): Signature
    {
        return try {
            this.privateKey.signatureForData(challenge)
        }
        catch (error: Exception)
        {
            throw error
        }
    }

    fun proveLive(connection: Connection)
    {
        val publicKeyData = publicKey.data ?: throw Exception(NametagError.nilPublicKey.toString())

        if (publicKeyData.size != expectedPublicKeySize)
        {
            throw Exception("Received a public key of ${publicKeyData.size} bytes, expected $expectedPublicKeySize bytes.")
        }

        try {
            connection.write(publicKeyData)
        }
        catch (error: NametagException)
        {
            throw Exception(NametagError.writeFailed.toString())
        }

        val challenge = connection.read(challengeSize) ?: throw Exception(NametagError.noChallengeReceived.toString())
        // FIXME: Do I need a try here? See NametagSwift
        val result = prove(challenge)
        val resultData = result.data

        if (resultData.size != expectedSignatureSize)
        {
            throw Exception(NametagError.challengeResultWrongSize.toString())
        }

        try {
            connection.write(resultData)
        }
        catch (error: NametagException)
        {
            throw Exception(NametagError.writeFailed.toString())
        }
    }

    fun endorse(data: ByteArray): Signature {
        return try {
            privateKey.signatureForData(data)
        }
        catch (error: Exception)
        {
            throw error
        }
    }
    fun verify(signature: Signature, data: ByteArray): Boolean
    {
        return publicKey.isValidSignature(signature, data)
    }

    enum class NametagError(val error: String)
    {
        noChallengeReceived("No challenge was recieved"),
        challengeResultWrongSize("Challenge result was the wrong size."),
        writeFailed("Write failed"),
        nilPublicKey("The public key cannot be nil."),
//        publicKeyWrongSize(receivedSize: Int, expectedSize: Int),
        verificationFailed("The verification failed."),
        noPublicKeyReceived("A public key was not recieved."),
        noSignatureReceived("A signature was not recieved.")
    }

    class NametagException(message: String) : Exception(message)
}