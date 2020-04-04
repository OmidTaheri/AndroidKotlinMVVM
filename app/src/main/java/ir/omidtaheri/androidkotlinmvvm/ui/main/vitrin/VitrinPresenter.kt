package ir.omidtaheri.androidkotlinmvvm.ui.main.vitrin

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.data.network.model.MovieByGenretResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.BasePresenter
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider
import javax.inject.Inject

class VitrinPresenter<V : VitrinMvpView>  @Inject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable),
    VitrinMvpPresenter<V> {

    override fun showMovieDetailActivity(movie_id: Int) {
        mvpView?.showMovieDetailActivity(movie_id)
    }

    override fun showAllMovieActivity(
        genre_id: Int,
        genre_name: String
    ) {
        mvpView!!.showAllMovieActivity(genre_id, genre_name)
    }

    override fun GetMovieListByGenre(genre_id: Int, list: Int) {
        if (list == 1) {
            mvpView!!.visibility_progressBar1(true)
        } else if (list == 2) {
            mvpView!!.visibility_progressBar2(true)
        } else if (list == 3) {
            mvpView!!.visibility_progressBar3(true)
        }
        mCompositeDisposable.add(
            mDataManager.GetMovieByGenre(genre_id, 1)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(Consumer<MovieByGenretResponse> { (data) ->
                    if (!isViewAttached) {
                        return@Consumer
                    }
                    if (list == 1) {
                        mvpView!!.visibility_progressBar1(false)
                        mvpView!!.setupList1(data)
                    } else if (list == 2) {
                        mvpView!!.visibility_progressBar2(false)
                        mvpView!!.setupList2(data)
                    } else if (list == 3) {
                        mvpView!!.visibility_progressBar3(false)
                        mvpView!!.setupList3(data)
                    }
                }, Consumer<Throwable?> { throwable ->
                    if (!isViewAttached) {
                        return@Consumer
                    }
                    if (list == 1) {
                        mvpView!!.error_load_List_1(handleApiError(throwable!!))
                    } else if (list == 2) {
                        mvpView!!.error_load_List_2(handleApiError(throwable!!))
                    } else if (list == 3) {
                        mvpView!!.error_load_List_3(handleApiError(throwable!!))
                    }
                })
        )
    }
}