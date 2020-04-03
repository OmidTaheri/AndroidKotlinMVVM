package ir.omidtaheri.androidkotlinmvvm.di.moduale

import dagger.Module
import dagger.Provides
import ir.omidtaheri.androidkotlinmvvm.BuildConfig
import ir.omidtaheri.androidkotlinmvvm.data.network.ApiInterface
import ir.omidtaheri.androidkotlinmvvm.di.Base_Url
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Base_Url
    fun provideBaseUrlString(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @Singleton
    fun provideGsonConverter(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        converter: Converter.Factory?,
        @Base_Url baseUrl: String?
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converter)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}