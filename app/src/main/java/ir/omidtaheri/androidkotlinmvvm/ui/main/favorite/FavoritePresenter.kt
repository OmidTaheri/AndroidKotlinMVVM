package ir.omidtaheri.androidkotlinmvvm.ui.main.favorite

import io.reactivex.disposables.CompositeDisposable
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.data.network.model.DetailMovieResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.BasePresenter
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider
import javax.inject.Inject

class FavoritePresenter<V : FavoriteMvpView>  @Inject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable),
    FavoriteMvpPresenter<V> {

    override fun getFavoriteList() {
        val favoriteMovies: List<DetailMovieResponse> =
            mDataManager.getFavoriteMovies()
        mvpView!!.setFavoriteList(favoriteMovies)
    }

    override fun showMovieDetailActivity(movie_id: Int) {
        mvpView!!.showMovieDetailActivity(movie_id)
    }
}