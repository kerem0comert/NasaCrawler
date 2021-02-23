package org.keremcomert.nasacrawler.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.keremcomert.nasacrawler.api.SourceApi
import org.keremcomert.nasacrawler.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * By default, the ApplicationComponent class is the point of injection for Hilt. It is the
 * base class that MainActivity inherits from, and its implementation is handled by the Android
 * SDK.
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    /**
     * The provide function needed for Hilt to inject the Retrofit API into the app. Defined as
     * Singleton, as multiple instances of the same API would be illogical.
     * Includes the baseURL for newsAPI to build upon and specifies the converter factory.
     * Since the response is received in JSON format, the Gson library is used to parse it,
     * hence it is the converter factory for the implementation.
     */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /**
     * Create an implementation of the API endpoints defined by the app's interface.
     * The app will interface with this SourceAPI instance, created by the retrofit library.
     */
    @Singleton
    @Provides
    fun provideSourceApi(r: Retrofit): SourceApi = r.create(SourceApi::class.java)

}