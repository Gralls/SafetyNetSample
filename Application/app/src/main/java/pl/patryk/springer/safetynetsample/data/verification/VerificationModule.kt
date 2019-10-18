package pl.patryk.springer.safetynetsample.data.verification

import pl.patryk.springer.safetynetsample.data.network.VerificationService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class VerificationModule {
    @Provides
    internal fun provideVerificationService(retrofit: Retrofit): VerificationService =
        retrofit.create(VerificationService::class.java)

    @Provides
    internal fun provideVerificationRepository(verificationService: VerificationService): VerificationRepository =
        VerificationRepository(verificationService)
}