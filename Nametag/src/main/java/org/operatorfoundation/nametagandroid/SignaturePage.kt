package org.operatorfoundation.nametagandroid

import org.operatorfoundation.keychainandroid.PublicKey

// FIXME: Signature is not yet implemented in KeychainAndroid
class SignaturePage(val signature: Signature, val publicKey: PublicKey)
{
    // FIXME: Not yet implemented
    fun isValidSignature(data: ByteArray): Boolean
    {
        return false
    }
}

