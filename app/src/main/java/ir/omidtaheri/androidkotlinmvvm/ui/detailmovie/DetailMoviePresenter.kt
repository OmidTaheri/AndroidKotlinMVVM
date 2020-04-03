package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.data.network.model.DetailMovieResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.BasePresenter
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider


class DetailMoviePresenter<V : DetailMovieMvpView>(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable),
    DetailMovieMvpPresenter<V> {


    override fun getDetailMovie(movie_id: Int) {
        mvpView?.showLoading()

        mCompositeDisposable.add(


            mDataManager.DetailMovie(movie_id)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(Consumer<DetailMovieResponse> { response ->
                    if (!isViewAttached) {

                    }
                    mvpView?.hideLoading()
                    mvpView?.showDetailMovie(response)
                }, Consumer<Throwable> { throwable ->
                    if (!isViewAttached) {

                    }
                    mvpView?.hideLoading()
                    mvpView?.error_load_data(handleApiError(throwable!!))
                })



        )


    }

    override fun addToFavorite(item: DetailMovieResponse) {
        mDataManager.addFavoriteMovie(item)
        mvpView?.showMessage("به علاقه مندی اضافه شد")
    }

    override fun deleteFromFavorite(movie_id: Int) {
        mDataManager.deleteFavoriteMovie(movie_id)
        mvpView?.showMessage("از علاقه مندی ها حذف شد")
    }

    override fun existInFavorite(item: Int): Boolean {
        return mDataManager.existInFavoriteMovie(item)
    }
}