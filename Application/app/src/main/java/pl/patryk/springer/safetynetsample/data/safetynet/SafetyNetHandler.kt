package pl.patryk.springer.safetynetsample.data.safetynet

import android.content.Context
import com.google.android.gms.safetynet.SafetyNet
import pl.patryk.springer.safetynetsample.data.verification.JwsResult
import pl.patryk.springer.safetynetsample.data.verification.Nonce
import io.reactivex.Observable
import pl.patryk.springer.safetynetsample.BuildConfig
import javax.inject.Inject


class SafetyNetHandler @Inject constructor(private val context: Context) {

    private val client by lazy { SafetyNet.getClient(context) }

    fun assessDevice(nonce: Nonce): Observable<JwsResult> = Observable.create { emitter ->
        client.attest(nonce.value, BuildConfig.SAFETYNET_API_KEY)
            .addOnSuccessListener { response ->
                emitter.onNext(JwsResult(response.jwsResult))
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }

}