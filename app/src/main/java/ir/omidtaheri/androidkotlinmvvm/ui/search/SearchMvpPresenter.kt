package ir.omidtaheri.androidkotlinmvvm.ui.search

import ir.omidtaheri.androidkotlinmvvm.di.PerActivity
import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpPresenter

@PerActivity
interface SearchMvpPresenter<V : SearchMvpView> : MvpPresenter<V> {
    fun searchFirstPage(query: String)
    fun searchNextPage(page: Int, query: String)
}