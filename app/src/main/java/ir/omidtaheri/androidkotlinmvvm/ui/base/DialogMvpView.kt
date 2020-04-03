package ir.omidtaheri.androidkotlinmvvm.ui.base

internal interface DialogMvpView : MvpView {
    fun dismissDialog(tag: String)
}