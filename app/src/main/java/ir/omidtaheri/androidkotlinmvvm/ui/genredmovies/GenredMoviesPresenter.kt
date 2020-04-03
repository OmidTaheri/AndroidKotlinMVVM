package ir.omidtaheri.androidkotlinmvvm.ui.genredmovies

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.data.network.model.MovieByGenretResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.BasePresenter
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider

class GenredMoviesPresenter<V : GenredMoviesMvpView> (
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable),
    GenredMoviesMvpPresenter<V> {

    override fun loadFirstPage(genre_id: Int) {

        mCompositeDisposable.add(
            mDataManager.GetMovieByGenre(genre_id, 1)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(object : Consumer<MovieByGenretResponse> {
                    @Throws(Exception::class)
                    override fun accept(response: MovieByGenretResponse) {
                        if (!isViewAttached) {
                            return
                        }
                        mvpView?.SetTotalPage(response.metadata.pageCount)
                        mvpView?.setupList(response.data, genre_id)
                    }
                }, object : Consumer<Throwable> {
                    @Throws(Exception::class)
                    override fun accept(throwable: Throwable) {
                        if (!isViewAttached) {
                            return
                        }
                        mvpView?.error_load_first_page(handleApiError(throwable), genre_id)
                    }
                })
        )
    }

    override fun loadNextPage(page: Int, genre_id: Int) {
        mCompositeDisposable.add(
            mDataManager.GetMovieByGenre(genre_id, page)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(object : Consumer<MovieByGenretResponse>{
                    @Throws(Exception::class)
                    override fun accept(response: MovieByGenretResponse) {
                        if (!isViewAttached) {
                            return
                        }
                        mvpView?.sucssed_load_next_page(response.data)
                    }
                }, object : Consumer<Throwable> {
                    @Throws(Exception::class)
                    override fun accept(throwable: Throwable) {
                        if (!isViewAttached) {
                            return
                        }
                        mvpView?.error_load_next_page(genre_id , page, handleApiError(throwable))
                    }
                })
        )
    }
}