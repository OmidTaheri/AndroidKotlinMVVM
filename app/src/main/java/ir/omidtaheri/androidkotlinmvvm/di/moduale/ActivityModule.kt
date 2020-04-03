package ir.omidtaheri.androidkotlinmvvm.di.moduale

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import ir.omidtaheri.androidkotlinmvvm.di.ActivityContext
import ir.omidtaheri.androidkotlinmvvm.di.PerActivity
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.DetailMovieMvpPresenter
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.DetailMovieMvpView
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.DetailMoviePresenter
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.gallery.GalleryMvpPresenter
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.gallery.GalleryMvpView
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.gallery.GalleryPresenter
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.info.IntroMvpPresenter
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.info.IntroMvpView
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.info.IntroPresenter
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.summary.SummaryMvpPresenter
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.summary.SummaryMvpView
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.summary.SummaryPresenter
import ir.omidtaheri.androidkotlinmvvm.ui.genredmovies.GenredMoviesMvpPresenter
import ir.omidtaheri.androidkotlinmvvm.ui.genredmovies.GenredMoviesMvpView
import ir.omidtaheri.androidkotlinmvvm.ui.genredmovies.GenredMoviesPresenter
import ir.omidtaheri.androidkotlinmvvm.ui.splash.SplashMvpPresenter
import ir.omidtaheri.androidkotlinmvvm.ui.splash.SplashMvpView
import ir.omidtaheri.androidkotlinmvvm.ui.splash.SplashPresenter
import ir.omidtaheri.androidkotlinmvvm.utils.rx.AppSchedulerProvider
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider


@Module
class ActivityModule(private val mActivity: AppCompatActivity) {

    @Provides
    @ActivityContext
    fun provideContext(): Context {
        return mActivity
    }

    @Provides
    fun provideActivity(): AppCompatActivity {
        return mActivity
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @PerActivity
    fun provideSplashPresenter(
        presenter: SplashPresenter<SplashMvpView>
    ): SplashMvpPresenter<SplashMvpView> {
        return presenter
    }

//    @Provides
//    @PerActivity
//    fun provideMainPresenter(
//        presenter: MainPresenter<MainMvpView>
//    ): MainMvpPresenter<MainMvpView> {
//        return presenter
//    }

    @Provides
    fun provideDetailMoviePresenter(
        presenter: DetailMoviePresenter<DetailMovieMvpView>
    ): DetailMovieMvpPresenter<DetailMovieMvpView> {
        return presenter
    }

//    @Provides
//    fun provideMVPagerAdapter(): VPagerMainAdapter {
//        return VPagerMainAdapter(mActivity.supportFragmentManager)
//    }

    @Provides
    fun provideLinearLayoutManager(activity: AppCompatActivity?): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

//    @Provides
//    @PerActivity
//    fun provideAboutUsPresenter(
//        presenter: AboutUsPresenter<AboutUsMvpView>
//    ): AboutUsMvpPresenter<AboutUsMvpView> {
//        return presenter
//    }

//    @Provides
//    @PerActivity
//    fun provideFavoritePresenter(
//        presenter: FavoritePresenter<FavoriteMvpView>
//    ): FavoriteMvpPresenter<FavoriteMvpView> {
//        return presenter
//    }

//    @Provides
//    @PerActivity
//    fun provideVitrinPresenter(
//        presenter: VitrinPresenter<VitrinMvpView>
//    ): VitrinMvpPresenter<VitrinMvpView> {
//        return presenter
//    }

//    @Provides
//    @PerActivity
//    fun provideSearchPresenter(
//        presenter: SearchPresenter<SearchMvpView>
//    ): SearchMvpPresenter<SearchMvpView> {
//        return presenter
//    }

//    @Provides
//    @PerActivity
//    fun provideCategoryPresenter(
//        presenter: CategoryPresenter<CategoryMvpView>
//    ): CategoryMvpPresenter<CategoryMvpView> {
//        return presenter
//    }

    @Provides
    @PerActivity
    fun provideIntroPresenter(
        presenter: IntroPresenter<IntroMvpView>
    ): IntroMvpPresenter<IntroMvpView> {
        return presenter
    }

    @Provides
    @PerActivity
    fun provideDescriptionPresenter(
        presenter: SummaryPresenter<SummaryMvpView>
    ): SummaryMvpPresenter<SummaryMvpView> {
        return presenter
    }

    @Provides
    @PerActivity
    fun provideGenredMoviesPresenter(
        presenter: GenredMoviesPresenter<GenredMoviesMvpView>
    ): GenredMoviesMvpPresenter<GenredMoviesMvpView> {
        return presenter
    }

    @Provides
    @PerActivity
    fun provideGalleryPresenter(
        presenter: GalleryPresenter<GalleryMvpView>
    ): GalleryMvpPresenter<GalleryMvpView> {
        return presenter
    }

}