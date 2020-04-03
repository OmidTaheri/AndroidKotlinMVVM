package ir.omidtaheri.androidkotlinmvvm.data.pref

import ir.omidtaheri.androidkotlinmvvm.data.network.model.DetailMovieResponse

interface PreferencesHelper {


    fun addFavoriteMovie(movie: DetailMovieResponse)

    fun deleteFavoriteMovie(movie_id: Int)

    fun existInFavoriteMovie(item_id: Int): Boolean

    fun getFavoriteMovies(): List<DetailMovieResponse>
}