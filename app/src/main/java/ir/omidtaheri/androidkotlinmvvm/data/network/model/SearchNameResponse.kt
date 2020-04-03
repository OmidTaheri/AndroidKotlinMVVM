package ir.omidtaheri.androidkotlinmvvm.data.network.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator



data class SearchNameResponse(val data: List<Movie>, val metadata: Metadata?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Movie)!!,
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

    companion object CREATOR : Creator<SearchNameResponse> {
        override fun createFromParcel(parcel: Parcel): SearchNameResponse {
            return SearchNameResponse(parcel)
        }

        override fun newArray(size: Int): Array<SearchNameResponse?> {
            return arrayOfNulls(size)
        }
    }


}