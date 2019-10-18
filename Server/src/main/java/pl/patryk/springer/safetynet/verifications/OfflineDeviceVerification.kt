package pl.patryk.springer.safetynet.verifications

import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.json.webtoken.JsonWebSignature
import pl.patryk.springer.safetynet.AttestationStatement
import pl.patryk.springer.safetynet.JwsResult
import org.apache.http.conn.ssl.DefaultHostnameVerifier
import java.security.cert.X509Certificate
import javax.net.ssl.SSLException

object OfflineDeviceVerification : DeviceVerification {

    override fun parseJws(jws: JwsResult): AttestationStatement? {
        val parsedJws = JsonWebSignature.parser(JacksonFactory.getDefaultInstance())
            .setPayloadClass(AttestationStatement::class.java).parse(jws.result)
        return if (isCertificateValid(
                parsedJws.verifySignature()
            )
        ) {
            parsedJws.payload as? AttestationStatement
        } else {
            null
        }
    }

    private fun isCertificateValid(jwsCertificatePart: X509Certificate): Boolean {
        try {
            DefaultHostnameVerifier().verify("attest.android.com", jwsCertificatePart)
            return true
        } catch (e: SSLException) {
            e.printStackTrace()
        }
        return false
    }
}