package ir.omidtaheri.androidkotlinmvvm.data

import android.content.Context
import io.reactivex.Observable
import ir.omidtaheri.androidkotlinmvvm.data.network.ApiHelper
import ir.omidtaheri.androidkotlinmvvm.data.network.model.DetailMovieResponse
import ir.omidtaheri.androidkotlinmvvm.data.network.model.SendMovieRequest
import ir.omidtaheri.androidkotlinmvvm.data.pref.PreferencesHelper
import ir.omidtaheri.androidkotlinmvvm.di.ApplicationContext
import javax.inject.Inject

class AppDataManager  @Inject constructor(
    @ApplicationContext val context: Context,
    val preferencesHelper: PreferencesHelper,
    val apiHelper: ApiHelper
) : DataManager {


    override fun addFavoriteMovie(movie: DetailMovieResponse) {
        preferencesHelper.addFavoriteMovie(movie)
    }

    override fun deleteFavoriteMovie(movie_id: Int) {
        preferencesHelper.deleteFavoriteMovie(movie_id)
    }

    override fun existInFavoriteMovie(item_id: Int)= preferencesHelper.existInFavoriteMovie(item_id)


    override fun getFavoriteMovies() = preferencesHelper.getFavoriteMovies()

    override fun MovieList(page: Int) = apiHelper.MovieList(page)


    override fun SearchName(name: String, page: Int) = apiHelper.SearchName(name, page)


    override fun SendMovie(request: SendMovieRequest) = apiHelper.SendMovie(request)


    override fun DetailMovie(movie_id: Int): Observable<DetailMovieResponse> =
        apiHelper.DetailMovie(movie_id)


    override fun GenresList() = apiHelper.GenresList()


    override fun GetMovieByGenre(genre_id: Int, page: Int) =
        apiHelper.GetMovieByGenre(genre_id, page)

}