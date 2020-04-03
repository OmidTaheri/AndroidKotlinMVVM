package ir.omidtaheri.androidkotlinmvvm.ui.splash

import android.content.Context
import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpPresenter

interface SplashMvpPresenter<V : SplashMvpView> : MvpPresenter<V> {

    fun delayToNextActivity(ctx: Context)

}