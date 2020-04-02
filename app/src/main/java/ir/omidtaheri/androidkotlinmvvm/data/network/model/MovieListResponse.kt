package ir.omidtaheri.androidkotlinmvvm.data.network.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator


data class MovieListResponse(val data: List<Movie?>?, val metadata: Metadata?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Movie),
        parcel.readParcelable(Metadata::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
        parcel.writeParcelable(metadata, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<MovieListResponse> {
        override fun createFromParcel(parcel: Parcel): MovieListResponse {
            return MovieListResponse(parcel)
        }

        override fun newArray(size: Int): Array<MovieListResponse?> {
            return arrayOfNulls(size)
        }
    }


}