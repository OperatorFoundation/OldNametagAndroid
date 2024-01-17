package org.operatorfoundation.transmissionnametag

import org.operatorfoundation.keychainandroid.Keychain
import org.operatorfoundation.keychainandroid.PublicKey
import org.operatorfoundation.nametagandroid.Nametag
import java.util.logging.Logger

class NametagClientConnection(baseConnection: Connection, keychain: Keychain, logger: Logger): AuthenticatingConnection(baseConnection, keychain, logger)
{
    var open = true


//    public enum NametagClientConnectionError: Error
//    {
//        case readFailed
//        case couldNotLoadDocument
//        case keyEncodingFailed
//        case nametagInitFailed
//        case connectionFailed
//        case serverSigningKeyMismatch
//        case writeFailed
//        case closed
//        case badPort(String)
//    }
}