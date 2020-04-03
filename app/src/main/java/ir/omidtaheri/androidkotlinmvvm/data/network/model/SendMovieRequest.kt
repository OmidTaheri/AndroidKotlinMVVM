package ir.omidtaheri.androidkotlinmvvm.data.network.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator


data class SendMovieRequest(
    val title: String?,
    val imdb_id: String?,
    val country: String?,
    val year:Int
) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(imdb_id)
        parcel.writeString(country)
        parcel.writeInt(year)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<SendMovieRequest> {
        override fun createFromParcel(parcel: Parcel): SendMovieRequest {
            return SendMovieRequest(parcel)
        }

        override fun newArray(size: Int): Array<SendMovieRequest?> {
            return arrayOfNulls(size)
        }
    }


}