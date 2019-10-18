package pl.patryk.springer.safetynet

import pl.patryk.springer.safetynet.verifications.OfflineDeviceVerification
import pl.patryk.springer.safetynet.verifications.OnlineDeviceVerification
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.apache.commons.codec.binary.Hex
import java.security.SecureRandom
import java.text.DateFormat
import java.util.*


val random by lazy { SecureRandom() }
var lastNonce: Nonce? = null
var lastTimestamp: Long? = null

val appInfo = Properties().apply { load(Application::class.java.getResourceAsStream("/app.properties")) }

fun Application.main() {

    val server = embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            gson {
                setDateFormat(DateFormat.LONG)
                setPrettyPrinting()
            }
        }

        routing {
            get("/nonce") {
                lastTimestamp = System.currentTimeMillis()
                val nonceBase = "SafetyNet Test $lastTimestamp"
                lastNonce =
                    Nonce.newInstance(
                        nonceBase,
                        random
                    )
                if (lastNonce == null) {
                    call.respond(HttpStatusCode.InternalServerError)
                } else {
                    call.respond(HttpStatusCode.OK, lastNonce!!)
                }
            }

            post("/verifiedRequest") {
                val result = call.receive<JwsResult>()
                val attestationStatement = if (result.verifyOnline) {
                    OnlineDeviceVerification.parseJws(result)
                } else {
                    OfflineDeviceVerification.parseJws(result)
                }
                if (attestationStatement == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    System.err.println("Failed to parse and verify attestation statement.")
                } else if (isAttestationStatementValid(
                        attestationStatement
                    )
                ) {
                    call.respond(HttpStatusCode.OK, attestationStatement)
                } else {
                    call.respond(HttpStatusCode.Unauthorized)
                }

            }
        }
    }
    server.start(wait = true)
}

fun isAttestationStatementValid(attestationStatement: AttestationStatement): Boolean {
    var isValid = true

    //Check if nonce in statement is equal to those one generated in first step
    if (attestationStatement.nonce != null && lastNonce?.value != null && !attestationStatement.nonce!!.contentEquals(
            lastNonce!!.value
        )
    ) {
        isValid = false
        System.err.println("Nonces are different")
    }

    //Check if Google timestamp is in reliable range from server lastTimestamp
    val minuteAfterLastTimestamp: Long = lastTimestamp?.plus(60 * 1000) ?: 0L
    if (attestationStatement.timestampMs > minuteAfterLastTimestamp) {
        isValid = false
        System.err.println("Timestamp is not in range")
    }

    //Check if package name is OK
    if (attestationStatement.apkPackageName != appInfo.getProperty("package")) {
        isValid = false
        System.err.println("Apk package name was different")
    }

    //Check certificates
    if (attestationStatement.apkCertificateDigestSha256.isNotEmpty()) {
        val cer =
            Hex.decodeHex(appInfo.getProperty("certFingerprint").toCharArray())
        if (!attestationStatement.apkCertificateDigestSha256.first().contentEquals(cer)) {
            isValid = false
            System.err.println("Certificates fingerprint are different")
        }
    } else {
        isValid = false
        System.err.println("No apk certificate")
    }

    //Check if the profile of the device matches profile that passed Google compatibility testing
    if (!attestationStatement.isCtsProfileMatch) {
        isValid = false
        System.err.println("Device's profile doesn't match compatibility testing")
    }

    //Check if device wasn't tampered
    if (!attestationStatement.hasBasicIntegrity()) {
        isValid = false
        System.err.println("Device was tampered")
    }

    if (isValid) {
        println("All checks passed")
    }
    return isValid
}