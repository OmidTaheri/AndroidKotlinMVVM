package ir.omidtaheri.androidkotlinmvvm.data.network

import io.reactivex.rxjava3.core.Observable
import ir.omidtaheri.androidkotlinmvvm.data.network.model.*

class AppApiHelper(val mApiInterface: ApiInterface) : ApiHelper {

    override fun MovieList(page: Int): Observable<MovieListResponse> {

        return mApiInterface.MovieList(page)
    }

    override fun SearchName(name: String, page: Int): Observable<SearchNameResponse> {
        return mApiInterface.SearchName(name, page)
    }

    override fun SendMovie(request: SendMovieRequest): Observable<SendMovieResponse> {
        return mApiInterface.SendMovie(request)
    }

    override fun DetailMovie(movie_id: Int): Observable<DetailMovieResponse> {

        return mApiInterface.DetailMovie(movie_id)
    }

    override fun GenresList(): Observable<List<GenresListResponse>> {
        return mApiInterface.GenresList()
    }

    override fun GetMovieByGenre(genre_id: Int, page: Int): Observable<MovieByGenretResponse> {
        return mApiInterface.GetMovieByGenre(genre_id, page)
    }
}