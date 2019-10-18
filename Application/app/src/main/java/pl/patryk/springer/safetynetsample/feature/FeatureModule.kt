package pl.patryk.springer.safetynetsample.feature

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import pl.patryk.springer.safetynetsample.feature.main.MainActivityModule

@Suppress("unused")
@Module(
    includes = [
        MainActivityModule::class
    ]
)
abstract class FeatureModule {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}
