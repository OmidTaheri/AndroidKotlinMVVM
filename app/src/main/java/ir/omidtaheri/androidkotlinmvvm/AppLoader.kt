package ir.omidtaheri.androidkotlinmvvm

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class AppLoader : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}