package pl.patryk.springer.safetynetsample.application

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.patryk.springer.safetynetsample.data.network.NetworkModule
import pl.patryk.springer.safetynetsample.data.safetynet.SafetyNetModule
import pl.patryk.springer.safetynetsample.data.verification.VerificationModule
import pl.patryk.springer.safetynetsample.feature.FeatureModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        SafetyNetModule::class,
        VerificationModule::class,
        NetworkModule::class,
        FeatureModule::class
    ]
)
internal interface ApplicationComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<App>
}
