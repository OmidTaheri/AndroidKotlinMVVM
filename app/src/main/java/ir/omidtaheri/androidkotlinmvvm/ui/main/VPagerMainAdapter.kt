package ir.omidtaheri.androidkotlinmvvm.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ir.omidtaheri.androidkotlinmvvm.ui.main.category.CategoryFragment
import ir.omidtaheri.androidkotlinmvvm.ui.main.favorite.FavoriteFragment
import ir.omidtaheri.androidkotlinmvvm.ui.main.vitrin.VitrinFragment

class VPagerMainAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val PAGE_COUNT = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteFragment.newInstance()
            1 -> CategoryFragment.newInstance()
            2 -> VitrinFragment.newInstance()
            else -> VitrinFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "علاقه مندی ها"
            1 -> "ژانرها"
            2 -> "ویترین"
            else -> ""
        }
    }
}