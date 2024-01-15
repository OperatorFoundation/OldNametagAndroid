package org.operatorfoundation.transmissionnametag

import org.operatorfoundation.keychainandroid.*
import org.operatorfoundation.nametagandroid.Nametag
import org.operatorfoundation.transmission.Transmission
import org.operatorfoundation.transmission.TransmissionConnection
import java.util.logging.Logger

object AuthenticatedConnection
{
    lateinit var publicKey: PublicKey
    lateinit var network: TransmissionConnection

//     init(_ base: any TransmissionTypes.Connection, _ logger: Logger) throws
    init {
        val base: TransmissionConnection
        val logger: Logger
    }
}