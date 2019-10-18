package pl.patryk.springer.safetynetsample.data.network

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.patryk.springer.safetynetsample.BuildConfig
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .writeTimeout(TIMEOUT, SECONDS)
            .readTimeout(TIMEOUT, SECONDS)
            .connectTimeout(TIMEOUT, SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    internal fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    internal fun provideRxJavaAdapterFactory(): CallAdapter.Factory =
        RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    internal fun provideAuthRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJavaCallAdapterFactory: CallAdapter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJavaCallAdapterFactory)
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(okHttpClient)
        .build()

    companion object {
        private const val TIMEOUT = 60L
    }

}