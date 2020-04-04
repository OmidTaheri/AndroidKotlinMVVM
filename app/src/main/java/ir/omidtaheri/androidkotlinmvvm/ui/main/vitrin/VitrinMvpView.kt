package ir.omidtaheri.androidkotlinmvvm.ui.main.vitrin

import ir.omidtaheri.androidkotlinmvvm.data.network.model.Movie
import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpView

interface VitrinMvpView : MvpView {
    fun setupList1(items_list: List<Movie>)
    fun setupList2(items_list: List<Movie>)
    fun setupList3(items_list: List<Movie>)
    fun showMovieDetailActivity(movie_id: Int)
    fun showAllMovieActivity(genre_id: Int, genre_name: String)
    fun visibility_progressBar1(show: Boolean)
    fun visibility_progressBar2(show: Boolean)
    fun visibility_progressBar3(show: Boolean)
    fun error_load_List_1(message: Int)
    fun error_load_List_2(message: Int)
    fun error_load_List_3(message: Int)
}