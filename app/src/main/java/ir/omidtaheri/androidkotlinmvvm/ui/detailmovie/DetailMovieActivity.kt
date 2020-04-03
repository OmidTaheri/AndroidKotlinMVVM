package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.data.network.model.DetailMovieResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseActivity


class DetailMovieActivity : BaseActivity(), DetailMovieMvpView,
    AppBarLayout.OnOffsetChangedListener {

    lateinit var mPresenter: DetailMovieMvpPresenter<DetailMovieMvpView>

    @BindView(R.id.main_toolbar)
    lateinit var mtoolbar: Toolbar

    @BindView(R.id.main_appbar)
    lateinit var appbar: AppBarLayout

    @BindView(R.id.main_collapsing)
    lateinit var mainCollapsing: CollapsingToolbarLayout

    @BindView(R.id.tablayout)
    lateinit var tablayout: TabLayout

    @BindView(R.id.vpager)
    lateinit var vpager: ViewPager


    @BindView(R.id.favorite_button)
    lateinit var fab: FloatingActionButton

    @BindView(R.id.main_backdrop)
    lateinit var mainBackdrop: ImageView

    @BindView(R.id.ratingBar)
    lateinit var ratingBar: RatingBar

    @BindView(R.id.flexbox_genre)
    lateinit var flexboxGenre: FlexboxLayout

    @BindView(R.id.time)
    lateinit var time: TextView

    var mIsAvatarShown = true
    var mMaxScrollSize = 0
    var movie_id = 0
    var ParentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie_bytechnic)

        mPresenter.onAttach(this)
        setUp()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        if (mMaxScrollSize == 0) mMaxScrollSize = appBarLayout.getTotalScrollRange()
        val percentage = Math.abs(i) * 100 / mMaxScrollSize
        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false
            fab.animate()
                .scaleY(0F).scaleX(0F)
                .setDuration(200)
                .start()
            fab.setClickable(mIsAvatarShown)
        }
        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true
            fab.animate()
                .scaleY(1F).scaleX(1F)
                .start()
            fab.setClickable(mIsAvatarShown)
        }
    }

    override fun setUp() {
        setSupportActionBar(mtoolbar)
        getSupportActionBar()?.setTitle("")
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        mainCollapsing.setExpandedTitleTypeface(
            Typeface.createFromAsset(
                getAssets(),
                "fonts/roboto_regular.ttf"
            )
        )
        mainCollapsing.setCollapsedTitleTypeface(
            Typeface.createFromAsset(
                getAssets(),
                "fonts/roboto_regular.ttf"
            )
        )
        appbar.addOnOffsetChangedListener(this)
        mMaxScrollSize = appbar.getTotalScrollRange()
        movie_id = getIntent().getExtras()?.getInt("movie_id") ?: 0
        ParentTag = getIntent().getExtras()?.getString("ParentTag") ?: ""
        if (mPresenter!!.existInFavorite(movie_id)) {
            fab.setImageResource(R.drawable.heart)
        } else {
            fab.setImageResource(R.drawable.heart_outline)
        }
        mPresenter!!.getDetailMovie(movie_id)
    }

    override fun showDetailMovie(detailMovie: DetailMovieResponse) {
        fab.setOnClickListener(View.OnClickListener {
            if (mPresenter.existInFavorite(movie_id)) {
                mPresenter.deleteFromFavorite(detailMovie.id)
                fab.setImageResource(R.drawable.heart_outline)
            } else {
                mPresenter.addToFavorite(detailMovie)
                fab.setImageResource(R.drawable.heart)
            }
        })
        mainCollapsing.setTitle(detailMovie.title)
        Glide.with(this@DetailMovieActivity)
            .load(detailMovie.poster)
            .apply(RequestOptions().placeholder(R.drawable.film_placeholder))
            .into(mainBackdrop)
        System.out.println("detailMovie.getPoster()" + detailMovie.poster)
        ratingBar.rating = detailMovie.imdbRating?.toFloat() ?: 0F
        time.setText(detailMovie.runtime?.replace("min", "دقیقه"))
        val vPagerAdapter =
            VPagerDetailAdapter(getSupportFragmentManager(), detailMovie)
        vpager.setAdapter(vPagerAdapter)
        tablayout.setupWithViewPager(vpager)
        vpager.setCurrentItem(2)
//        val typeface: Typeface =
//            TypefaceUtils.load(getAssets(), getString(R.string.font_path_regular))
        for (i in 0 until tablayout.getTabCount()) {
            val tv =
                LayoutInflater.from(this).inflate(R.layout.custom_tab_item_main, null) as TextView
            tv.setText(vpager.getAdapter()?.getPageTitle(i))
            //tv.setTypeface(typeface)
            tablayout.getTabAt(i)?.setCustomView(tv)
        }
        for (i in 0 until (detailMovie.genres?.size ?: 0)) {
            val subchild: View =
                getLayoutInflater().inflate(R.layout.single_chip, null)
            val tv =
                subchild.findViewById<View>(R.id.txt_title) as TextView
            tv.setText(detailMovie.genres?.get(i))
            flexboxGenre.addView(subchild)
        }
    }

    override fun error_load_data(message: Int) {
        onError(message)
    }

    override fun onDestroy() {
        mPresenter!!.onDetach()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return false
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun onBackPressed() {
//        if (ParentTag == FavoriteFragment::class.java.getSimpleName()) {
//            startActivity(MainActivity.getStartIntent(this))
//            finish()
//        } else {
//            super.onBackPressed()
//        }
    }

    companion object {
        /////
        private const val PERCENTAGE_TO_ANIMATE_AVATAR = 20
        fun getStartIntent(
            context: Context,
            movie_id: Int,
            ParentTag: String
        ): Intent {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra("movie_id", movie_id)
            intent.putExtra("ParentTag", ParentTag)
            return intent
        }
    }
}