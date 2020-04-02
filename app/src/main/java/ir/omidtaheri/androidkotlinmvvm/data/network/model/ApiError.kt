package ir.omidtaheri.androidkotlinmvvm.data.network.model

import com.google.gson.annotations.SerializedName

data class ApiError (val errorCode: Int?,
                     @SerializedName("status_code")  val statusCode:String?,
                     val message: String?)