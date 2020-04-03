package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.summary

import io.reactivex.disposables.CompositeDisposable
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.ui.base.BasePresenter
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider


class SummaryPresenter<V : SummaryMvpView>  (
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SummaryMvpPresenter<V>