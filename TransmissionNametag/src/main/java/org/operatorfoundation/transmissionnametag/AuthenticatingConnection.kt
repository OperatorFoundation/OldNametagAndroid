package org.operatorfoundation.transmissionnametag

import org.operatorfoundation.keychainandroid.*
import org.operatorfoundation.nametagandroid.Nametag
import org.operatorfoundation.transmission.*
import java.util.logging.Logger

object AuthenticatingConnection
{
    lateinit var publicKey: PublicKey
    lateinit var network: TransmissionConnection

    // init(_ base: any TransmissionTypes.Connection, _ keychain: KeychainProtocol, _ logger: Logger) throws
    init {
        val base: TransmissionConnection
        val keychain: Keychain
        val logger: Logger
    }
}