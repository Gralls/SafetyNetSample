package pl.patryk.springer.safetynetsample.application

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {
    @Provides
    fun context(app: App): Context = app.applicationContext
}