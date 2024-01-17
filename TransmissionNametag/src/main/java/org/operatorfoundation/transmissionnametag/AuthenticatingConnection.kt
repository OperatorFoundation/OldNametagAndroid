package org.operatorfoundation.transmissionnametag

import org.operatorfoundation.keychainandroid.Keychain
import org.operatorfoundation.keychainandroid.PublicKey
import org.operatorfoundation.nametagandroid.Nametag
import org.operatorfoundation.transmission.Connection
import java.util.logging.Logger

open class AuthenticatingConnection(baseConnection: Connection, keychain: Keychain, logger: Logger)
{
    private val protectedConnection: Connection
    private val protectedPublicKey: PublicKey

    val connection: Connection
        get() {
            return protectedConnection
        }
    val publicKey: PublicKey
        get() {
            return protectedPublicKey
        }

    init {
        val nametag = Nametag(keychain)
        protectedPublicKey = nametag.publicKey
        protectedConnection = baseConnection
    }

}