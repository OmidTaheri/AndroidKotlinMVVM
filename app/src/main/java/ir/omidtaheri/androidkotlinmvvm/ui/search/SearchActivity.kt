package ir.omidtaheri.androidkotlinmvvm.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.mancj.materialsearchbar.MaterialSearchBar
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.data.network.model.Movie
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseActivity
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.DetailMovieActivity


class SearchActivity : BaseActivity(), SearchMvpView,
    SearchRecyclerAdapter.Callback,
    MaterialSearchBar.OnSearchActionListener {

    lateinit var mPresenter: SearchMvpPresenter<SearchMvpView>
    lateinit var scrollListener: PaginationScrollListener

    private var isLoading = false
    private var isLastPage = false
    var TOTAL_PAGES = 0

    private var currentPage = PAGE_START

    lateinit var adapter: SearchRecyclerAdapter

    ////
    @BindView(R.id.searchView)
    lateinit var searchView: MaterialSearchBar

    @BindView(R.id.search_list)
    lateinit var searchList: RecyclerView

    @BindView(R.id.main_progress)
    lateinit var mainProgress: ProgressBar

    @BindView(R.id.error_text)
    lateinit var errorText: TextView

    @BindView(R.id.error_btn_retry)
    lateinit var errorBtnRetry: TextView

    @BindView(R.id.error_layout)
    lateinit var errorLayout: ConstraintLayout

    @BindView(R.id.search_button)
    lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search2)

        mPresenter!!.onAttach(this)
        setUp()
    }

    override fun setUp() {
        searchButton!!.setOnClickListener { doMySearch(searchView.getText()) }
        searchView.setOnSearchActionListener(this)
        searchView.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                if (!searchView.getText().equals("")) {
                    searchButton!!.visibility = View.VISIBLE
                } else {
                    searchButton!!.visibility = View.GONE
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun doMySearch(query: String) {
        errorLayout.setVisibility(View.GONE)
        hideKeyboard()
        mPresenter!!.searchFirstPage(query)
    }

    override fun onDestroy() {
        mPresenter!!.onDetach()
        super.onDestroy()
    }

    override fun sucssed_load_first_page(list: List<Movie>) {
        mainProgress!!.visibility = View.GONE
        if (currentPage <= TOTAL_PAGES && TOTAL_PAGES != 1) {
            adapter!!.addLoadingFooter()
        } else isLastPage = true
    }

    override fun error_load_first_page(message: Int, query: String) {
        if (errorLayout.getVisibility() === View.GONE) {
            errorLayout.setVisibility(View.VISIBLE)
            mainProgress!!.visibility = View.GONE
            errorText.setText(getResources().getString(message))
        }
        errorBtnRetry!!.setOnClickListener {
            errorLayout.setVisibility(View.GONE)
            mainProgress!!.visibility = View.VISIBLE
            mPresenter!!.searchFirstPage(query)
        }
    }

    override fun sucssed_load_next_page(list: List<Movie>) {
        adapter!!.removeLoadingFooter()
        isLoading = false
        adapter.addAll(list)
        if (currentPage != TOTAL_PAGES) {
            adapter!!.addLoadingFooter()
        } else isLastPage = true
    }

    override fun error_load_next_page(
        query: String,
        page: Int,
        error_message: Int
    ) {
        adapter!!.showRetry(true, getResources().getString(error_message), query, page)
    }

    override fun SetTotalPage(total_page: Int) {
        TOTAL_PAGES = total_page
    }

    override fun setupListofSearch(
        items_list: List<Movie>,
        query: String
    ) {


        //////////////////////////////
        val manager =
            MyGridAutofitLayoutManager(this, searchList)
        val layoutManager: GridLayoutManager
        layoutManager = GridLayoutManager(this, manager.getmColumnNumber())
        searchList.setLayoutManager(layoutManager)
        //////////
        adapter = SearchRecyclerAdapter(items_list.toMutableList(), this@SearchActivity, manager)
        adapter!!.setCallback(this)
        searchList.setAdapter(adapter)
        layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter!!.getItemViewType(position)) {
                    SearchRecyclerAdapter.VIEW_TYPE_EMPTY -> {
                        searchList.removeOnScrollListener(scrollListener)
                        layoutManager.getSpanCount()
                    }
                    SearchRecyclerAdapter.VIEW_TYPE_NORMAL -> 1
                    SearchRecyclerAdapter.LOADING -> layoutManager.getSpanCount()
                    else -> 1
                }
            }
        })
        scrollListener = object :
            PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                mPresenter!!.searchNextPage(currentPage, query!!)
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
        searchList.addOnScrollListener(scrollListener)
        if (items_list != null && items_list.size > 0) {
            sucssed_load_first_page(items_list)
        } else {
            mainProgress!!.visibility = View.GONE
        }
    }

    override fun showMovieDetailActivity(movie_id: Int) {
        val intent: Intent =
            DetailMovieActivity.getStartIntent(this, movie_id, this.javaClass.simpleName)
        startActivity(intent)
    }

    override fun onItemClick(movie_id: Int) {
        showMovieDetailActivity(movie_id)
    }

    override fun retryPageLoad(query: String?, page: Int) {
        mPresenter!!.searchNextPage(page, query!!)
    }

    override fun onSearchStateChanged(enabled: Boolean) {}
    override fun onSearchConfirmed(text: CharSequence) {

        doMySearch(text.toString())
    }

    override fun onButtonClicked(buttonCode: Int) {}
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

    override fun visibility_progressBar(show: Boolean) {
        if (show) {
            mainProgress!!.visibility = View.VISIBLE
        } else {
            mainProgress!!.visibility = View.GONE
        }
    }

    companion object {
        private const val PAGE_START = 1

        fun getStartIntent(context: Context?): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }
}