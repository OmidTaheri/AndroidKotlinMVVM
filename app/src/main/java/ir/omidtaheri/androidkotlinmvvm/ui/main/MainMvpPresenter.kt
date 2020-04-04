package ir.omidtaheri.androidkotlinmvvm.ui.main

import ir.omidtaheri.androidkotlinmvvm.di.PerActivity
import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpPresenter

@PerActivity
interface MainMvpPresenter<V : MainMvpView> : MvpPresenter<V> {
    fun showSearchActivity()
    fun showAboutUsActivity()
}