package ir.omidtaheri.androidkotlinmvvm.ui.main.category

import ir.omidtaheri.androidkotlinmvvm.data.network.model.GenresListResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.MvpView

interface CategoryMvpView : MvpView {
    fun setListGenre(items_list: List<GenresListResponse>)
    fun showGenreDetailActivity(genre_id: Int, genre_name: String)
    fun visibility_progressBar(show: Boolean)
    fun error_load_List(message: Int)
}