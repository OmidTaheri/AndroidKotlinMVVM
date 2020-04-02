package ir.omidtaheri.androidkotlinmvvm.data.network.model

import android.os.Parcel
import android.os.Parcelable

data class GenresListResponse(val id: Int, val name: String?) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GenresListResponse> {
        override fun createFromParcel(parcel: Parcel): GenresListResponse {
            return GenresListResponse(parcel)
        }

        override fun newArray(size: Int): Array<GenresListResponse?> {
            return arrayOfNulls(size)
        }
    }
}