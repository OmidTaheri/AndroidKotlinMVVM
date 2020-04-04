package ir.omidtaheri.androidkotlinmvvm.ui.genredmovies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.data.network.model.Movie
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseActivity
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.DetailMovieActivity
import javax.inject.Inject

class GenredMoviesActivity : BaseActivity(), GenredMoviesMvpView,
    GenredMoviesRecyclerAdapter.Callback {

    @Inject
    lateinit  var mPresenter: GenredMoviesMvpPresenter<GenredMoviesMvpView>
    lateinit var scrollListener: PaginationScrollListener


    private var isLoading = false
    private var isLastPage = false
    var TOTAL_PAGES = 0
    private var currentPage = PAGE_START


    private lateinit var adapter: GenredMoviesRecyclerAdapter


    /////////////////
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.movie_list)
    lateinit var movieList: RecyclerView

    @BindView(R.id.main_progress)
    lateinit  var mainProgress: ProgressBar

    @BindView(R.id.error_text)
    lateinit var errorText: TextView

    @BindView(R.id.error_btn_retry)
    lateinit var errorBtnRetry: TextView

    @BindView(R.id.error_layout)
    lateinit  var errorLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_movie)


        getActivityComponent().inject(this)

        setUnBinder(ButterKnife.bind(this))

        mPresenter.onAttach(this)

        setUp()
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

    override  fun setUp() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val genre_id = intent.extras?.getInt("genre_id")
        val genre_name = intent.extras?.getString("genre_name")
        supportActionBar?.setTitle(genre_name)
        mPresenter.loadFirstPage(genre_id!!)
    }

    override fun sucssed_load_first_page(list: List<Movie>) {
        mainProgress.visibility = View.GONE
        if (currentPage <= TOTAL_PAGES && TOTAL_PAGES != 1) {
            adapter.addLoadingFooter()
        } else isLastPage = true
    }

    override fun error_load_first_page(message: Int, genre_id: Int) {
        if (errorLayout.visibility == View.GONE) {
            errorLayout.visibility = View.VISIBLE
            mainProgress.visibility = View.GONE
            errorText.text = resources.getString(message)
        }
        errorBtnRetry.setOnClickListener {
            errorLayout.visibility = View.GONE
            mainProgress.visibility = View.VISIBLE
            mPresenter.loadFirstPage(genre_id)
        }
    }

    override fun sucssed_load_next_page(list: List<Movie>) {
        adapter.removeLoadingFooter()
        isLoading = false
        adapter.addAll(list)
        if (currentPage != TOTAL_PAGES) {
            adapter.addLoadingFooter()
        } else isLastPage = true
    }

    override fun error_load_next_page(
        genre_id: Int,
        page: Int,
        error_message: Int
    ) {
        adapter.showRetry(true, resources.getString(error_message), genre_id, page)
    }

    override fun SetTotalPage(total_page: Int) {
        TOTAL_PAGES = total_page
    }

    override fun setupList(items_list: List<Movie>, category_id: Int) {

        val manager =
            MyGridAutofitLayoutManager(
                this,
                movieList
            )

        val layoutManager: GridLayoutManager
        layoutManager = GridLayoutManager(this, manager.getmColumnNumber())
        movieList.layoutManager = layoutManager
        //////////
        adapter = GenredMoviesRecyclerAdapter(items_list.toMutableList(), this, manager)
        adapter.setCallback(this)
        movieList.adapter = adapter
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    GenredMoviesRecyclerAdapter.VIEW_TYPE_EMPTY -> {
                        movieList.removeOnScrollListener(scrollListener)
                        layoutManager.spanCount
                    }
                    GenredMoviesRecyclerAdapter.VIEW_TYPE_NORMAL -> 1
                    GenredMoviesRecyclerAdapter.LOADING -> layoutManager.spanCount
                    else -> 1
                }
            }
        }
        scrollListener = object : PaginationScrollListener(layoutManager)  {
             override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                mPresenter.loadNextPage(currentPage, category_id)
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
            override fun totalPageCount(): Int {
                return TOTAL_PAGES
            }
        }
        movieList.addOnScrollListener(scrollListener)
        if ( items_list.size > 0) {
            sucssed_load_first_page(items_list)
        } else {
            mainProgress.visibility = View.GONE
        }
    }

    override fun showMovieDetailActivity(movie_id: Int) {
        val intent =
            DetailMovieActivity.getStartIntent(this, movie_id, this.javaClass.simpleName)
        startActivity(intent)
    }

    override fun onItemClick(movie_id: Int) {
        showMovieDetailActivity(movie_id)
    }

    override fun retryPageLoad(category_id: Int, page: Int) {
        mPresenter.loadNextPage(page, category_id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
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

    companion object {
        private const val PAGE_START = 1
        fun getStartIntent(
            context: Context,
            genre_id: Int,
            genre_name: String
        ): Intent {
            val intent = Intent(context, GenredMoviesActivity::class.java)
            intent.putExtra("genre_id", genre_id)
            intent.putExtra("genre_name", genre_name)
            return intent
        }
    }
}