package ir.omidtaheri.androidkotlinmvvm.ui.base

 interface MvpPresenter<V : MvpView> {
    fun onAttach(mvpView: V)
    fun onDetach()
    fun handleApiError(throwable: Throwable): Int
}