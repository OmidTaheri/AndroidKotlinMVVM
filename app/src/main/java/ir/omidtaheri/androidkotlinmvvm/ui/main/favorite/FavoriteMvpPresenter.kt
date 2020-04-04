package ir.omidtaheri.androidkotlinmvvm.ui.main.favorite

import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpPresenter

interface FavoriteMvpPresenter<V : FavoriteMvpView> : MvpPresenter<V> {

    fun getFavoriteList()

    fun showMovieDetailActivity(movie_id: Int)
}