package ir.omidtaheri.androidkotlinmvvm.ui.main.vitrin

import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpPresenter

interface VitrinMvpPresenter<V : VitrinMvpView> : MvpPresenter<V> {
    fun showMovieDetailActivity(movie_id: Int)
    fun showAllMovieActivity(genre_id: Int, genre_name: String)
    fun GetMovieListByGenre(genre_id: Int, list: Int)
}