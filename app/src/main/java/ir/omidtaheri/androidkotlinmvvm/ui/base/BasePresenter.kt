package ir.omidtaheri.androidkotlinmvvm.ui.base

import io.reactivex.disposables.CompositeDisposable
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.net.ssl.HttpsURLConnection


class BasePresenter<V : MvpView>(
   var mDataManager: DataManager,
   var mSchedulerProvider: SchedulerProvider,
   var mCompositeDisposable: CompositeDisposable) : MvpPresenter<V> {

    var mvpView: V? = null
        private set

    override fun onAttach(mvpView: V) {
        this.mvpView = mvpView
    }

    override fun onDetach() {
        mCompositeDisposable.dispose()
        mvpView = null
    }

    var isViewAttached: Boolean = false
        get() = mvpView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }


    override fun handleApiError(throwable: Throwable): Int {
        return if (throwable is HttpException) {
            val response = throwable.response()
            when (response?.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED, HttpsURLConnection.HTTP_FORBIDDEN -> R.string.ERROR_UNAUTHORIZED
                HttpsURLConnection.HTTP_INTERNAL_ERROR, HttpsURLConnection.HTTP_NOT_FOUND, HttpsURLConnection.HTTP_UNAVAILABLE -> R.string.ERROR_NOT_FOUND
                HttpsURLConnection.HTTP_BAD_REQUEST -> R.string.ERROR_GENERAL
                else -> R.string.ERROR_GENERAL
            }
        } else if (throwable is SocketTimeoutException) {
            R.string.ERROR_GENERAL
        } else if (throwable is IOException) {
            R.string.ERROR_GENERAL
        } else if (throwable is IllegalStateException) {
            R.string.ERROR_GENERAL
        } else {
            R.string.ERROR_GENERAL
        }
    }

    class MvpViewNotAttachedException : RuntimeException(
        "Please call Presenter.onAttach(MvpView) before" +
                " requesting data to the Presenter"
    )



}