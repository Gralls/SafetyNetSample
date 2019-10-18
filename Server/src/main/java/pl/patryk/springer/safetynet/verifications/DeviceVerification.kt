package pl.patryk.springer.safetynet.verifications

import pl.patryk.springer.safetynet.AttestationStatement
import pl.patryk.springer.safetynet.JwsResult

interface DeviceVerification {
    fun parseJws(jws: JwsResult): AttestationStatement?
}