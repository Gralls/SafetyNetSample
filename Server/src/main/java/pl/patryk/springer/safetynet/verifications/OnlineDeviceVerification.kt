package pl.patryk.springer.safetynet.verifications

import pl.patryk.springer.safetynet.AttestationStatement
import pl.patryk.springer.safetynet.JwsResult

object OnlineDeviceVerification : DeviceVerification {

    //TODO make request to google api to verify jws
    override fun parseJws(jws: JwsResult): AttestationStatement? {

        return null
    }

}