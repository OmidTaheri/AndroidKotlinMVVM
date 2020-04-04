package ir.omidtaheri.androidkotlinmvvm.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.tabs.TabLayout
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseActivity
import ir.omidtaheri.androidkotlinmvvm.ui.search.SearchActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var mPresenter: MainMvpPresenter<MainMvpView>

    @Inject
    lateinit var vPagerAdapter: VPagerMainAdapter

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.tablayout)
    lateinit var tablayout: TabLayout

    @BindView(R.id.vpager)
    lateinit var vpager: ViewPager

    ///
    var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        doubleBackToExitPressedOnce = true
        showMessage(getString(R.string.double_exit_message))
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getActivityComponent().inject(this)

        setUnBinder(ButterKnife.bind(this))

        mPresenter.onAttach(this)


        setUp()
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

      override fun setUp() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(R.string.app_name)
        vpager.adapter = vPagerAdapter
        tablayout.setupWithViewPager(vpager)
        vpager.currentItem = 2
//        val typeface: Typeface = TypefaceUtils.load(
//            assets,
//            getString(R.string.font_path_regular)
//        )
        for (i in 0 until tablayout.tabCount) {
            val tv = LayoutInflater.from(this).inflate(
                R.layout.custom_tab_item_main,
                null
            ) as TextView
            tv.text = vpager.adapter!!.getPageTitle(i)
            //tv.setTypeface(typeface)
            tablayout.getTabAt(i)!!.customView = tv
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                mPresenter.showSearchActivity()
                true
            }
            R.id.action_aboutus -> {
                mPresenter.showAboutUsActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showSearchActivity() {
        startActivity(SearchActivity.getStartIntent(this))
    }

    override fun showAboutUsActivity() {
        //startActivity(AboutUsActivity.getStartIntent(this))
    }

    override fun onFragmentAttached() {}
    override fun onFragmentDetached(tag: String) {}

    companion object {
        fun getStartIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}