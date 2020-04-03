package ir.omidtaheri.androidkotlinmvvm.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.omidtaheri.androidkotlinmvvm.data.network.model.DetailMovieResponse
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class AppPreferenceHelper (val context :Context, val prefFileName :String): PreferencesHelper {


    companion object{
        private const val PREF_KEY_FAVORITES_ITEMS = "PREF_KEY_FAVORITES_ITEMS"
    }


    private val mPrefs :SharedPreferences = context.getSharedPreferences(prefFileName,Context.MODE_PRIVATE)


    override fun addFavoriteMovie(movie: DetailMovieResponse) {
        var break_boolean = false
        val gson = Gson()
        val editor: SharedPreferences.Editor = mPrefs.edit()

        val list: String? = mPrefs.getString(PREF_KEY_FAVORITES_ITEMS, "")

        var movieFromShared: List<DetailMovieResponse> = ArrayList()

        val type = object : TypeToken<List<DetailMovieResponse>>() {}.type

        movieFromShared = gson.fromJson(list, type)


        val jsonNewmovieToAdd = gson.toJson(movie)
        var jsonArrayMovie = JSONArray()

        try {
            if (list?.length != 0) {
                jsonArrayMovie = JSONArray(list)

                for (item in movieFromShared) {
                    if (item.id == movie.id) {
                        break_boolean = true
                        break
                    }
                }



                if (!break_boolean) {
                    jsonArrayMovie.put(JSONObject(jsonNewmovieToAdd))
                }
            } else {
                jsonArrayMovie.put(JSONObject(jsonNewmovieToAdd))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }


        editor.putString(PREF_KEY_FAVORITES_ITEMS,
            jsonArrayMovie.toString()
        )
        editor.apply()
    }

    override fun deleteFavoriteMovie(movie_id: Int) {

        val gson = Gson()
        val editor = mPrefs.edit()

        val list = mPrefs.getString(PREF_KEY_FAVORITES_ITEMS, "")

        var movieFromShared: List<DetailMovieResponse> = ArrayList()
        val type = object : TypeToken<List<DetailMovieResponse?>?>() {}.type


        movieFromShared = gson.fromJson(list, type)


        for (i in movieFromShared.indices) {
            if (movieFromShared[i].id === movie_id) {
                movieFromShared.drop(i)
            }
        }

        val FavoriteString = if (movieFromShared != null || movieFromShared?.size != 0) {
            gson.toJson(movieFromShared)
        } else {
            ""
        }

        editor.putString(PREF_KEY_FAVORITES_ITEMS, FavoriteString)
        editor.apply()
    }


    override fun existInFavoriteMovie(item_id: Int): Boolean {
        val gson = Gson()

        val list = mPrefs.getString(PREF_KEY_FAVORITES_ITEMS, "")

        var movieFromShared: List<DetailMovieResponse> = ArrayList()

        val type =
            object : TypeToken<List<DetailMovieResponse>>() {}.type

        movieFromShared = gson.fromJson(list, type)

        if (movieFromShared != null) {
            for (item in movieFromShared ) {
                if (item.id === item_id) {
                    return true
                }
            }
        }

        return false
    }

    override fun getFavoriteMovies(): List<DetailMovieResponse> {
        val gson = Gson()

        val list = mPrefs.getString(PREF_KEY_FAVORITES_ITEMS, "")


        var movieFromShared: List<DetailMovieResponse> = ArrayList()
        val type = object : TypeToken<List<DetailMovieResponse?>?>() {}.type


        movieFromShared = gson.fromJson(list, type)

        if (movieFromShared == null) {
            movieFromShared = ArrayList()
        }

        return movieFromShared
    }
}