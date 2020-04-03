package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie

import ir.omidtaheri.androidkotlinmvvm.data.network.model.DetailMovieResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpPresenter


interface DetailMovieMvpPresenter<V : DetailMovieMvpView> : MvpPresenter<V> {
    fun getDetailMovie(movie_id: Int)
    fun addToFavorite(item: DetailMovieResponse)
    fun deleteFromFavorite(movie_id: Int)
    fun existInFavorite(item: Int): Boolean
}