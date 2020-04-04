package ir.omidtaheri.androidkotlinmvvm

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import ir.omidtaheri.androidkotlinmvvm.di.component.ApplicationComponent
import ir.omidtaheri.androidkotlinmvvm.di.component.DaggerApplicationComponent
import ir.omidtaheri.androidkotlinmvvm.di.moduale.ApplicationModule

class AppLoader : Application() {

//    @Inject
//    lateinit var mDataManager: DataManager

    private lateinit  var mApplicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()


        mApplicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this)).build()

        mApplicationComponent.inject(this)

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    fun getComponent(): ApplicationComponent {
        return mApplicationComponent
    }


    fun setComponent(applicationComponent: ApplicationComponent) {
        mApplicationComponent = applicationComponent
    }
}