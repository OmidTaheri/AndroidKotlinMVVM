package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.gallery

import io.reactivex.disposables.CompositeDisposable
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.ui.base.BasePresenter
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider
import javax.inject.Inject


class GalleryPresenter<V : GalleryMvpView>   @Inject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), GalleryMvpPresenter<V>