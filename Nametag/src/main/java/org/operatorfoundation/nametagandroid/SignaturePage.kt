package org.operatorfoundation.nametagandroid

import org.operatorfoundation.keychainandroid.PublicKey
import org.operatorfoundation.keychainandroid.Signature

class SignaturePage(val signature: Signature, val publicKey: PublicKey)
{
    fun isValidSignature(data: ByteArray): Boolean
    {
        return publicKey.isValidSignature(signature, data)
    }
}

