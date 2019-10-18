package pl.patryk.springer.safetynetsample.data.safetynet

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SafetyNetModule {

    @Singleton
    @Provides
    fun safetyNetHandler(context: Context): SafetyNetHandler = SafetyNetHandler(context)
}