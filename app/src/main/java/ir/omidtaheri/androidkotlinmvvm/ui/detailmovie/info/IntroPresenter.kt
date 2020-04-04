package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.info

import io.reactivex.disposables.CompositeDisposable
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.ui.base.BasePresenter
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider
import javax.inject.Inject

class IntroPresenter<V : IntroMvpView>  @Inject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), IntroMvpPresenter<V>