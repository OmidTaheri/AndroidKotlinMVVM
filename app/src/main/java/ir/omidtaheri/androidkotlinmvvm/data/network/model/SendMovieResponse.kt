package ir.omidtaheri.androidkotlinmvvm.data.network.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.SerializedName


data class SendMovieResponse(
    val id: Int,
    val title: String?,
    val poster: String?,
    val year: Int,
    val director: String?,
    val country: String?,
    @SerializedName("imdb_rating") val imdbRating: String?,
    @SerializedName("imdb_votes") val imdbVotes: String?,
    @SerializedName("imdb_id") val imdbId: String?


) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(poster)
        parcel.writeInt(year)
        parcel.writeString(director)
        parcel.writeString(country)
        parcel.writeString(imdbRating)
        parcel.writeString(imdbVotes)
        parcel.writeString(imdbId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<SendMovieResponse> {
        override fun createFromParcel(parcel: Parcel): SendMovieResponse {
            return SendMovieResponse(parcel)
        }

        override fun newArray(size: Int): Array<SendMovieResponse?> {
            return arrayOfNulls(size)
        }
    }


}