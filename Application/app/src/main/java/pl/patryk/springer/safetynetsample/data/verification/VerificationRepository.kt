package pl.patryk.springer.safetynetsample.data.verification

import pl.patryk.springer.safetynetsample.data.network.VerificationService
import pl.patryk.springer.safetynetsample.data.verification.JwsResult
import javax.inject.Inject

class VerificationRepository @Inject constructor(private val verificationService: VerificationService) {

    fun getNonce() = verificationService.getNonce()
    fun sendAttestationResult(jwsResult: JwsResult) = verificationService.verifiedRequest(jwsResult)
}