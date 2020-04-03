package ir.omidtaheri.androidkotlinmvvm.ui.base

interface MvpView {


    fun showLoading()

    fun hideLoading()


    fun onError( resId: Int)

    fun onError(message: String?)

    fun showMessage(message: String?)

    fun showMessage(resId: Int)

    fun isNetworkConnected(): Boolean

    fun hideKeyboard()
}