package org.operatorfoundation.nametagandroid

import android.provider.ContactsContract.Data
import org.operatorfoundation.keychainandroid.KeyType

import org.operatorfoundation.keychainandroid.PublicKey
import org.operatorfoundation.keychainandroid.
import org.operatorfoundation.keychainandroid.PrivateKey
import org.operatorfoundation.transmission.Connection
import org.operatorfoundation.transmission.Transmission
import org.operatorfoundation.transmission.TransmissionConnection
import java.security.Signature
import kotlin.random.Random

class Nametag {
    val challengeSize: Int = 64
    val expectedPublicKeySize: Int = 65
    val expectedSignatureSize: Int = 64

    fun check(challenge: Data, clientPublicKey: PublicKey, signature: Signature)
    {
        // FIXME: isValidSignature is not yet implemented in KeychainAndroid
        clientPublicKey.isValidSignature(signature, challenge) ?: throw Exception(NametagError.verificationFailed.toString())
    }

    fun checkLive(connection: TransmissionConnection): PublicKey
    {
        val clientPublicKeyData = connection.read(expectedPublicKeySize) ?: throw Exception(NametagError.noPublicKeyReceived.toString())
        val clientPublicKey = PublicKey.new(clientPublicKeyData)
        val challenge = Random.nextBytes(challengeSize)
        val wroteChallenge = connection.write(challenge)

        if (!wroteChallenge)
        {
            throw error(NametagError.writeFailed)
        }

        val signatureData = connection.read(expectedSignatureSize) ?: throw Exception(NametagError.noSignatureReceived.toString())

        val signature = try {
            Signature(SignatureType.P256, signatureData)
        }
        catch (error: Exception)
        {
            throw error
        }

        return
    }


    // TODO: Use KeychainLibrary
    val privateKey: PrivateKey = TODO()

    public val publicKey: PublicKey

    var data = publicKey.data

    init {
        val privateSigningKey = keychain.retrieveOrGeneratePrivateKey("Nametag", KeyType.P256Signing) ?: null
        if (privateSigningKey != null) {
            this.privateKey = privateSigningKey
            this.publicKey = privateSigningKey.publicKey
        }
    }

//    fun prove(challenge: Data): Signature {
//
//        return try {
//            this.privateKey.signature(for (challenge))
//        }
//    }

    fun proveLive(connection: Connection)
    {

    }

    // FIXME: Digest has not yet been implemented in KeychainAndroid
    fun endorse(digest: Digest): Signature {
        throw Exception("endorse(digest:) not yet implemented")
    }

    fun endorse(data: ByteArray): Signature {
        throw Exception("endorse(data:) not yet implemented")
    }

//    fun endorse<T>(object: T): EndorsedTypedDocument<T> where T: Codable {
//        return null
//    }

    fun verify(signature: Signature, digest: Digest): Boolean {
        return false
    }

    fun verify(signature: Signature, data: ByteArray): Boolean {
        return false
    }

    enum class NametagError(val error: String)
    {
        noChallengeReceived("No challenge was recieved"),
        challengeResultWrongSize("Challenge result was the wrong size."),
        writeFailed("Write failed"),
        nilPublicKey("The public key cannot be nil."),
//        publicKeyWrongSize(receivedSize: Int, expectedSize: Int)
        verificationFailed("The verification failed."),
        noPublicKeyReceived("A public key was not recieved."),
        noSignatureReceived("A signature was not recieved.")
    }

    class NametagException(message: String) : Exception(message)
}