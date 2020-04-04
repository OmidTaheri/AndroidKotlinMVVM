package ir.omidtaheri.androidkotlinmvvm.ui.main.category

import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpPresenter

interface CategoryMvpPresenter<V : CategoryMvpView> : MvpPresenter<V> {
    fun getGenreList()
    fun showGenreDetailActivity(genre_id: Int, genre_name: String?)
}