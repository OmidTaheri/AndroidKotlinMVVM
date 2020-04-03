package ir.omidtaheri.androidkotlinmvvm.data.network.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.SerializedName


data class Movie(
    val id: Int,
    val title: String?,
    val poster: String?,
    val year: String?,
    val country: String?,
    @SerializedName("imdb_rating") val imdbRating: String?,
    val genres: List<String?>?,
    val images: List<String?>?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    )


    constructor() : this(-1,"","","","","", listOf<String>(), listOf<String>())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(poster)
        parcel.writeString(year)
        parcel.writeString(country)
        parcel.writeString(imdbRating)
        parcel.writeStringList(genres)
        parcel.writeStringList(images)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

}