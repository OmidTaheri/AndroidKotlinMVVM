package ir.omidtaheri.androidkotlinmvvm.data.network

class ApiEndPoint private constructor() {

    companion object {
        const val ENDPOINT_MOVIES_LIST = "api/v1/movies"
        const val ENDPOINT_SEND_MOVIE = "api/v1/movies/multi"
        const val ENDPOINT_GENRES = "api/v1/genres"
    }
}