package ir.omidtaheri.androidkotlinmvvm.ui.splash

import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpView

interface SplashMvpView  : MvpView{


    fun launchMainActivity()
    fun showErrorLayout()
}