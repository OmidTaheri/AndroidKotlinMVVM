package ir.omidtaheri.androidkotlinmvvm.ui.genredmovies

import ir.omidtaheri.androidkotlinmvvm.data.network.model.Movie
import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpView

interface GenredMoviesMvpView : MvpView {

    fun sucssed_load_first_page(list: List<Movie>)

    fun error_load_first_page(message: Int, genre_id: Int)

    fun sucssed_load_next_page(list: List<Movie>)

    fun error_load_next_page(genre_id: Int, page: Int, message: Int)

    fun SetTotalPage(total_page: Int)

    fun setupList(
        items_list: List<Movie>,
        genre_id: Int
    )

    fun showMovieDetailActivity(movie_id: Int)
}