package ir.omidtaheri.androidkotlinmvvm.ui.search

import ir.omidtaheri.androidkotlinmvvm.data.network.model.Movie
import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpView

interface SearchMvpView : MvpView {
    fun setupListofSearch(
        items_list: List<Movie>,
        query: String
    )

    fun sucssed_load_first_page(list: List<Movie>)
    fun error_load_first_page(message: Int, query: String)
    fun sucssed_load_next_page(list: List<Movie>)
    fun error_load_next_page(query: String, page: Int, message: Int)
    fun SetTotalPage(total_page: Int)
    fun showMovieDetailActivity(movie_id: Int)
    fun visibility_progressBar(show: Boolean)
}