package ir.omidtaheri.androidkotlinmvvm.ui.main

import io.reactivex.disposables.CompositeDisposable
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.ui.base.BasePresenter
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider

class MainPresenter<V : MainMvpView>(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable),
    MainMvpPresenter<V> {
    override fun showSearchActivity() {
        mvpView?.showSearchActivity()
    }

    override fun showAboutUsActivity() {
        mvpView?.showAboutUsActivity()
    }

    companion object {
        private const val TAG = "MainPresenter"
    }
}