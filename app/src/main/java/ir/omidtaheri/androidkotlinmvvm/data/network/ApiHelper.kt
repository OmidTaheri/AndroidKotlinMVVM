package ir.omidtaheri.androidkotlinmvvm.data.network

import io.reactivex.rxjava3.core.Observable
import ir.omidtaheri.androidkotlinmvvm.data.network.model.*


interface ApiHelper {

    fun MovieList(page :Int): Observable<MovieListResponse>

    fun SearchName(name:String , page:Int):Observable<SearchNameResponse>

    fun SendMovie(request: SendMovieRequest): Observable<SendMovieResponse>

    fun DetailMovie(movie_id: Int): Observable<DetailMovieResponse>

    fun GenresList(): Observable<List<GenresListResponse>>

    fun GetMovieByGenre(
        genre_id: Int,
        page: Int): Observable<MovieByGenretResponse>


}