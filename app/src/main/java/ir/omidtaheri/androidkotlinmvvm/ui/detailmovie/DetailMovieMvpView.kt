package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie

import ir.omidtaheri.androidkotlinmvvm.data.network.model.DetailMovieResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpView


interface DetailMovieMvpView : MvpView {
    fun showDetailMovie(detailMovie: DetailMovieResponse)
    fun error_load_data(message: Int)
}