package pl.patryk.springer.safetynetsample.feature.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.patryk.springer.safetynetsample.data.safetynet.SafetyNetHandler
import pl.patryk.springer.safetynetsample.data.verification.AttestationStatement
import pl.patryk.springer.safetynetsample.data.verification.JwsResult
import pl.patryk.springer.safetynetsample.data.verification.VerificationRepository
import timber.log.Timber
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(
    private val safetyNetHandler: SafetyNetHandler,
    private val verificationRepository: VerificationRepository
) :
    ViewModel() {

    private val disposables = CompositeDisposable()
    val attestationResult = MutableLiveData<JwsResult>()
    val verificationResult = MutableLiveData<AttestationStatement>()

    fun assessDevice() {
        verificationRepository.getNonce()
            .subscribeOn(Schedulers.io())
            .flatMap {
                safetyNetHandler.assessDevice(it)
            }
            .subscribeBy(
                onNext = {
                    attestationResult.postValue(it)
                },
                onError = Timber::e
            ).addTo(disposables)

    }

    fun verifyResult(onlineCheck: Boolean) {
        val jws = attestationResult.value?.copy(verifyOnline = onlineCheck)
        jws?.let {
            verificationRepository.sendAttestationResult(it)
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onNext = { verification ->
                        verificationResult.postValue(verification)
                    },
                    onError = Timber::e
                )
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}
