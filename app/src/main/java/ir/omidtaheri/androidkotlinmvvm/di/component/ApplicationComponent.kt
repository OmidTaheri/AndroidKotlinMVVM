package ir.omidtaheri.androidkotlinmvvm.di.component

import android.app.Application
import android.content.Context
import dagger.Component
import ir.omidtaheri.androidkotlinmvvm.AppLoader
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.di.ApplicationContext
import ir.omidtaheri.androidkotlinmvvm.di.moduale.ApplicationModule
import ir.omidtaheri.androidkotlinmvvm.di.moduale.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {


    fun inject(app: AppLoader)


    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun getDataManager(): DataManager
}