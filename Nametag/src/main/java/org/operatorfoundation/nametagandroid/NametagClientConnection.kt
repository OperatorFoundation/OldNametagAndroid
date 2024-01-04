package org.operatorfoundation.nametagandroid

import org.operatorfoundation.keychainandroid.*
import org.operatorfoundation.nametagandroid.*
import org.operatorfoundation.transmission.Transmission
import org.operatorfoundation.transmission.TransmissionConnection
import java.util.logging.Logger

class NametagClientConnection: AuthenticatingConnection
{
    public var publicKey: PublicKey
    {
        return null
    }

//    lateinit var network: TransmissionConnection {
//        return null
//    }

//    val protectedConnection: TransmissionConnection
//    val protectedPublicKey: PublicKey

//    val logger: Logger
//    val nametag: Nametag
//    val straw = Straw()
//    val lock = DispatchSemaphore(value: 1)

    var open = true

    constructor(config: ShadowConfig.ShadowClientConfig, keychain: Keychain, logger: Logger)
    {
        val nametag = Nametag(keychain: Keychain) ?: return

        val parts = config.serverAddress.split(separator: ":")
        val hostPart = String(parts[0])
        val portPart = String(parts[1])
        val portInt = Int(string: portPart)

        val protectedConnection = ShadowTransmissionClientConnection(host: hostPart, port: portInt, config: config, logger: logger) ?: return

        // try self.init(protectedConnection, nametag, logger)
    }

    public constructor(base: TransmissionTypes.Connection, keychain: KeychainTypes.KeychainProtocol, logger: Logger)
    {
        val nametag = Nametag(keychain: keychain) ?: return

        this.protectedConnection = base
        this.protectedPublicKey = nametag.publicKey

        this.nametag = nametag
        this.logger = logger

//        try self.nametag.proveLive(connection: self.network)
    }

    public constructor(base: Transmission.Connection, nametag: Nametag, logger: Logger)
    {
        this.protectedConnection = base
        this.protectedPublicKey = nametag.publicKey

        this.nametag = nametag
        self.logger = logger

        try self.nametag.proveLive(connection: self.protectedConnection)
    }

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