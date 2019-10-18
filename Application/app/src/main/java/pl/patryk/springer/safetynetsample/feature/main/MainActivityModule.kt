package pl.patryk.springer.safetynetsample.feature.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.patryk.springer.safetynetsample.feature.ViewModelKey

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun provideMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel
}
