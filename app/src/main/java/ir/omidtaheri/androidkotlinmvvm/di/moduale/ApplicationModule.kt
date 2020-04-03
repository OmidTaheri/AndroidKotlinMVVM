package ir.omidtaheri.androidkotlinmvvm.di.moduale

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ir.omidtaheri.androidkotlinmvvm.data.AppDataManager
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.data.network.ApiHelper
import ir.omidtaheri.androidkotlinmvvm.data.network.AppApiHelper
import ir.omidtaheri.androidkotlinmvvm.data.pref.AppPreferenceHelper
import ir.omidtaheri.androidkotlinmvvm.data.pref.PreferencesHelper
import ir.omidtaheri.androidkotlinmvvm.di.ApplicationContext
import ir.omidtaheri.androidkotlinmvvm.di.PreferenceInfo
import ir.omidtaheri.androidkotlinmvvm.utils.AppConstants
import javax.inject.Singleton

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return mApplication
    }

    @Provides
    fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferenceHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @Singleton
    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }
//
//    @Provides
//    @Singleton
//    fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
//        return Builder()
//            .setDefaultFontPath("fonts/byekan.ttf")
//            .setFontAttrId(R.attr.fontPath)
//            .build()
//    }

}