package ir.omidtaheri.androidkotlinmvvm.ui.genredmovies

import ir.omidtaheri.androidkotlinmvvm.di.PerActivity
import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpPresenter

@PerActivity
interface GenredMoviesMvpPresenter<V : GenredMoviesMvpView> : MvpPresenter<V> {
    fun loadFirstPage(genre_id: Int)
    fun loadNextPage(page: Int, genre_id: Int)
}