package ir.omidtaheri.androidkotlinmvvm.ui.main.category

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.data.network.model.GenresListResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.BasePresenter
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider
import javax.inject.Inject

class CategoryPresenter<V : CategoryMvpView>  @Inject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable),
    CategoryMvpPresenter<V> {

    override fun getGenreList() {
        mvpView!!.visibility_progressBar(true)
        mCompositeDisposable.add(
            mDataManager.GenresList()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(object : Consumer<List<GenresListResponse>> {

                    override fun accept(response: List<GenresListResponse>) {
                        if (!isViewAttached) {
                            return
                        }
                        mvpView!!.visibility_progressBar(false)
                        mvpView!!.setListGenre(response)
                    }
                }, object : Consumer<Throwable> {

                    override fun accept(throwable: Throwable) {
                        if (!isViewAttached) {
                            return
                        }
                        mvpView!!.visibility_progressBar(false)
                        mvpView!!.error_load_List(handleApiError(throwable))
                    }
                })
        )
    }

    override fun showGenreDetailActivity(
        genre_id: Int,
        genre_name: String?
    ) {
        mvpView!!.showGenreDetailActivity(genre_id, genre_name!!)
    }
}