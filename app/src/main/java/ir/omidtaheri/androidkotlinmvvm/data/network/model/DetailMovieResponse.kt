package ir.omidtaheri.androidkotlinmvvm.data.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailMovieResponse (
    val id: Int,
    val title: String,
    val poster: String,
    val year: String,
    val rated: String?,
    val released: String?,
    val runtime: String?,
    val director: String?,
    val writer: String?,
    val actors: String?,
    val plot: String?,
    val country: String?,
    val awards: String?,
    val metascore: String?,
    @SerializedName("imdb_rating") val imdbRating: String?,
    @SerializedName("imdb_votes") val imdbVotes: String?,
    @SerializedName("imdb_id") val imdbId: String?,
    val type: String?,
    val genres: List<String>?,
    val images: List<String>?
) :Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(poster)
        parcel.writeString(year)
        parcel.writeString(rated)
        parcel.writeString(released)
        parcel.writeString(runtime)
        parcel.writeString(director)
        parcel.writeString(writer)
        parcel.writeString(actors)
        parcel.writeString(plot)
        parcel.writeString(country)
        parcel.writeString(awards)
        parcel.writeString(metascore)
        parcel.writeString(imdbRating)
        parcel.writeString(imdbVotes)
        parcel.writeString(imdbId)
        parcel.writeString(type)
        parcel.writeStringList(genres)
        parcel.writeStringList(images)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailMovieResponse> {
        override fun createFromParcel(parcel: Parcel): DetailMovieResponse {
            return DetailMovieResponse(parcel)
        }

        override fun newArray(size: Int): Array<DetailMovieResponse?> {
            return arrayOfNulls(size)
        }
    }


}