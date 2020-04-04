package ir.omidtaheri.androidkotlinmvvm.di.component

import dagger.Component
import ir.omidtaheri.androidkotlinmvvm.di.PerActivity
import ir.omidtaheri.androidkotlinmvvm.di.moduale.ActivityModule
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.DetailMovieActivity
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.gallery.GalleryFragment
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.info.IntroFragment
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.summary.SummaryFragment
import ir.omidtaheri.androidkotlinmvvm.ui.genredmovies.GenredMoviesActivity
import ir.omidtaheri.androidkotlinmvvm.ui.main.MainActivity
import ir.omidtaheri.androidkotlinmvvm.ui.main.category.CategoryFragment
import ir.omidtaheri.androidkotlinmvvm.ui.main.favorite.FavoriteFragment
import ir.omidtaheri.androidkotlinmvvm.ui.main.vitrin.VitrinFragment
import ir.omidtaheri.androidkotlinmvvm.ui.search.SearchActivity
import ir.omidtaheri.androidkotlinmvvm.ui.splash.SplashActivity


@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {


    fun inject(activity: MainActivity)

    fun inject(activity: SplashActivity)

    fun inject(activity: DetailMovieActivity)

//    fun inject(activity: AboutUsActivity)

    fun inject(activity: SearchActivity)

    fun inject(activity: GenredMoviesActivity)

    fun inject(fragment: CategoryFragment)

    fun inject(fragment: FavoriteFragment)

    fun inject(fragment: VitrinFragment)

    fun inject(fragment: IntroFragment)

    fun inject(fragment: SummaryFragment)

    fun inject(fragment: GalleryFragment)

}