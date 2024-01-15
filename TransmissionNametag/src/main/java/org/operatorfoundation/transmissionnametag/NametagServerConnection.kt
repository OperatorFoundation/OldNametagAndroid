package org.operatorfoundation.transmissionnametag

import org.operatorfoundation.keychainandroid.*
import org.operatorfoundation.transmission.*

class NametagServerConnection: org.operatorfoundation.transmissionnametag.AuthenticatedConnection
{
//    public var publicKey: PublicKey
////    {
////        return this.protectedPublicKey
////    }

//    public var network: TransmissionTypes.Connection
//    {
//        return this.protectedConnection
//    }

    val protectedConnection: TransmissionTypes.Connection
    val protectedPublicKey: PublicKey

//    public constructor(base: TransmissionTypes.Connection, logger: Logger)
//    {
//        this.protectedConnection = base
//        this.protectedPublicKey = try {
//            Nametag.checkLive(connection: base)
//        }
//    }
}