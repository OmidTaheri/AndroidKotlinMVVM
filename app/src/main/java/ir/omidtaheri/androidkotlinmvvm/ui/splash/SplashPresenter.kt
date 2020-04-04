package ir.omidtaheri.androidkotlinmvvm.ui.splash

import android.content.Context
import io.reactivex.Maybe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action

import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.ui.base.BasePresenter
import ir.omidtaheri.androidkotlinmvvm.utils.NetworkUtils
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SplashPresenter<V : SplashMvpView>  @Inject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SplashMvpPresenter<V> {


    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
    }

    override fun delayToNextActivity(ctx: Context) {
        Maybe.empty<Any>() // returns maybe instance that calls onComplete right away
            .delay(2, TimeUnit.SECONDS) // posting delay of 3 seconds
            .subscribeOn(mSchedulerProvider.io())
            .observeOn(mSchedulerProvider.ui())
            .doOnComplete(Action {
                if (NetworkUtils.isNetworkConnected(ctx)) {
                    Maybe.empty<Any>() // returns maybe instance that calls onComplete right away
                        .delay(
                            2,
                            TimeUnit.SECONDS
                        ) // posting delay of 3 seconds
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.ui())
                        .doOnComplete(Action {
                            if (!isViewAttached) {
                                return@Action
                            }
                            mvpView?.launchMainActivity()
                        })
                        .subscribe()
                } else {
                    if (!isViewAttached) {
                        return@Action
                    }
                    mvpView?.showErrorLayout()
                }
            })
            .subscribe()
    }
}