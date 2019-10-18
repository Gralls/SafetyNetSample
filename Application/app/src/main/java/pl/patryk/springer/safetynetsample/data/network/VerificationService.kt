package pl.patryk.springer.safetynetsample.data.network

import pl.patryk.springer.safetynetsample.data.verification.AttestationStatement
import pl.patryk.springer.safetynetsample.data.verification.JwsResult
import pl.patryk.springer.safetynetsample.data.verification.Nonce
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VerificationService {

    @GET("/nonce")
    fun getNonce(): Observable<Nonce>

    @POST("/verifiedRequest")
    fun verifiedRequest(@Body result: JwsResult): Observable<AttestationStatement>
}