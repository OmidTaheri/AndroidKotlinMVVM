package ir.omidtaheri.androidkotlinmvvm.data.network.model

import android.os.Parcel
import android.os.Parcelable


data class MovieByGenretResponse(val data: List<Movie>, val metadata: Metadata) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Movie)!!,
        parcel.readParcelable(Metadata::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
        parcel.writeParcelable(metadata, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieByGenretResponse> {
        override fun createFromParcel(parcel: Parcel): MovieByGenretResponse {
            return MovieByGenretResponse(parcel)
        }

        override fun newArray(size: Int): Array<MovieByGenretResponse?> {
            return arrayOfNulls(size)
        }
    }


}