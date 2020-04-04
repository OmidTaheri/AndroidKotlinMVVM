package ir.omidtaheri.androidkotlinmvvm.data.network


import io.reactivex.Observable
import ir.omidtaheri.androidkotlinmvvm.data.network.model.*
import retrofit2.http.*

interface ApiInterface {

    @GET(ApiEndPoint.ENDPOINT_MOVIES_LIST)
    fun MovieList(@Query("page") page: Int): Observable<MovieListResponse>

    @GET(ApiEndPoint.ENDPOINT_MOVIES_LIST)
    fun SearchName(
        @Query("q") name: String,
        @Query("page") page: Int
    ): Observable<SearchNameResponse>

    @POST(ApiEndPoint.ENDPOINT_SEND_MOVIE)
    fun SendMovie(@Body request: SendMovieRequest): Observable<SendMovieResponse>

    @GET(ApiEndPoint.ENDPOINT_MOVIES_LIST + "/{movie_id}")
    fun DetailMovie(@Path("movie_id") movie_id: Int): Observable<DetailMovieResponse>

    @GET(ApiEndPoint.ENDPOINT_GENRES)
    fun GenresList(): Observable<List<GenresListResponse>>

    @GET(ApiEndPoint.ENDPOINT_GENRES + "/{genre_id}/movies")
    fun GetMovieByGenre(
        @Path("genre_id") genre_id: Int,
        @Query("page") page: Int
    ): Observable<MovieByGenretResponse>

}