package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ir.omidtaheri.androidkotlinmvvm.data.network.model.DetailMovieResponse
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.gallery.GalleryFragment
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.info.IntroFragment
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.summary.SummaryFragment


class VPagerDetailAdapter(fm: FragmentManager, var detailMovie: DetailMovieResponse) :
    FragmentPagerAdapter(fm) {


    val PAGE_COUNT = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> GalleryFragment.newInstance(detailMovie.images!!)
            1 -> SummaryFragment.newInstance(detailMovie.plot)
            2 -> IntroFragment.newInstance(
                detailMovie.director!!,
                detailMovie.writer!!,
                detailMovie.year,
                detailMovie.country!!,
                detailMovie.actors!!,
                detailMovie.awards!!
            )
            else -> GalleryFragment.newInstance(detailMovie.images!!)
        }
    }

    override fun getCount() = PAGE_COUNT


    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "عکس ها"
            1 -> "خلاصه داستان"
            2 -> "توضیحات"
            else -> null
        }
    }


}