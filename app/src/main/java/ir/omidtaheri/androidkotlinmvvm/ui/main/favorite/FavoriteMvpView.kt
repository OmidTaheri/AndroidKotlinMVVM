package ir.omidtaheri.androidkotlinmvvm.ui.main.favorite

import ir.omidtaheri.androidkotlinmvvm.data.network.model.DetailMovieResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpView

interface FavoriteMvpView : MvpView {
    fun setFavoriteList(list: List<DetailMovieResponse>)
    fun showMovieDetailActivity(movie_id: Int)
}