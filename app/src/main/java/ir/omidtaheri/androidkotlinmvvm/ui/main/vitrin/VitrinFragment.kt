package ir.omidtaheri.androidkotlinmvvm.ui.main.vitrin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.data.network.model.Movie
import ir.omidtaheri.androidkotlinmvvm.di.component.ActivityComponent
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseFragment
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.DetailMovieActivity
import ir.omidtaheri.androidkotlinmvvm.ui.genredmovies.GenredMoviesActivity
import javax.inject.Inject

class VitrinFragment : BaseFragment(), VitrinMvpView,
    VitrinAdapter.Callback {

    @Inject
    lateinit  var mPresenter: VitrinMvpPresenter<VitrinMvpView>

    @BindView(R.id.title1)
    lateinit var title1: TextView

    @BindView(R.id.list1)
    lateinit var list1: RecyclerView

    @BindView(R.id.progressBar1)
    lateinit var progressBar1: ProgressBar

    @BindView(R.id.error_text)
    lateinit var erroreText: TextView

    @BindView(R.id.error_btn_retry)
    lateinit var errorBtnRetry: TextView

    @BindView(R.id.error_layout)
    lateinit var errorLayout: ConstraintLayout

    @BindView(R.id.list_layout1)
    lateinit var listLayout1: ConstraintLayout

    @BindView(R.id.title2)
    lateinit var title2: TextView

    @BindView(R.id.list2)
    lateinit var list2: RecyclerView

    @BindView(R.id.progressBar2)
    lateinit var progressBar2: ProgressBar

    @BindView(R.id.error_text2)
    lateinit var errorText2: TextView

    @BindView(R.id.error_btn_retry2)
    lateinit var errorBtnRetry2: TextView

    @BindView(R.id.error_layout2)
    lateinit  var errorLayout2: ConstraintLayout

    @BindView(R.id.list_layout2)
    lateinit var listLayout2: ConstraintLayout

    @BindView(R.id.title3)
    lateinit  var title3: TextView

    @BindView(R.id.list3)
    lateinit var list3: RecyclerView

    @BindView(R.id.progressBar3)
    lateinit var progressBar3: ProgressBar

    @BindView(R.id.error_text3)
    lateinit var errorText3: TextView

    @BindView(R.id.error_btn_retry3)
    lateinit  var errorBtnRetry3: TextView

    @BindView(R.id.error_layout3)
    lateinit var errorLayout3: ConstraintLayout

    @BindView(R.id.list_layout3)
    lateinit var listLayout3: ConstraintLayout

    @BindView(R.id.show_all1)
    lateinit  var showAll1: TextView

    @BindView(R.id.show_all2)
    lateinit  var showAll2: TextView

    @BindView(R.id.show_all3)
    lateinit  var showAll3: TextView

    @BindView(R.id.cl_root_view)
    lateinit var clRootView: CoordinatorLayout

    /////////////
    lateinit var items1: List<Movie>
    lateinit var items2: List<Movie>
    lateinit var items3: List<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(
            R.layout.fragment_vitrin,
            container,
            false
        )
        val component: ActivityComponent? = getActivityComponent()
        if (component != null) {
            component.inject(this)
            setUnBinder(ButterKnife.bind(this, v))
            mPresenter.onAttach(this)
        }
        return v
    }

    override fun setUp(view: View) {
        hideLoading()
        if ( !::items1.isInitialized ) {
            mPresenter.GetMovieListByGenre(3, 1)
        } else {
            setupList1(items1)
        }
        if (!::items2.isInitialized) {
            mPresenter.GetMovieListByGenre(2, 2)
        } else {
            setupList2(items2)
        }
        if (!::items3.isInitialized) {
            mPresenter.GetMovieListByGenre(9, 3)
        } else {
            setupList3(items3)
        }

        ///
        showAll1.setOnClickListener { mPresenter.showAllMovieActivity(3, "Action") }
        showAll2.setOnClickListener { mPresenter.showAllMovieActivity(2, "Drama") }
        showAll3.setOnClickListener { mPresenter.showAllMovieActivity(9, "Comedy") }
    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

    override fun onItemClick(movie_id: Int) {
        mPresenter.showMovieDetailActivity(movie_id)
    }

    override fun setupList1(items_list: List<Movie>) {
        progressBar1.visibility = View.GONE
        items1 = items_list
        val adapter = VitrinAdapter(items_list.toMutableList(), requireContext())
        adapter.setCallback(this)
        list1.adapter = adapter
        list1.isFocusable = false
        list1.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
    }

    override fun setupList2(items_list: List<Movie>) {
        progressBar2.visibility = View.GONE
        items2 = items_list
        val adapter = VitrinAdapter(items_list.toMutableList(),requireContext())
        adapter.setCallback(this)
        list2.adapter = adapter
        list2.isFocusable = false
        list2.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
    }

    override fun setupList3(items_list: List<Movie>) {
        progressBar3.visibility = View.GONE
        items3 = items_list
        val adapter = VitrinAdapter(items_list.toMutableList(), requireContext())
        adapter.setCallback(this)
        list3.adapter = adapter
        list3.isFocusable = false
        list3.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
    }

    override fun showMovieDetailActivity(movie_id: Int) {
        val intent = DetailMovieActivity.getStartIntent(
            requireContext(),
            movie_id,
            this.javaClass.simpleName
        )
        startActivity(intent)
    }

    override fun showAllMovieActivity(
        genre_id: Int,
        genre_name: String
    ) {
        val intent =
            GenredMoviesActivity.getStartIntent(requireContext(), genre_id, genre_name)
        startActivity(intent)
    }

    override fun visibility_progressBar1(show: Boolean) {
        if (show) {
            progressBar1.visibility = View.VISIBLE
        } else {
            progressBar1.visibility = View.GONE
        }
    }

    override fun visibility_progressBar2(show: Boolean) {
        if (show) {
            progressBar2.visibility = View.VISIBLE
        } else {
            progressBar2.visibility = View.GONE
        }
    }

    override fun visibility_progressBar3(show: Boolean) {
        if (show) {
            progressBar3.visibility = View.VISIBLE
        } else {
            progressBar3.visibility = View.GONE
        }
    }

    override fun error_load_List_1(message: Int) {
        if (errorLayout.visibility == View.GONE) {
            errorLayout.visibility = View.VISIBLE
            progressBar1.visibility = View.GONE
            erroreText.text = resources.getString(message)
        }
        errorBtnRetry.setOnClickListener {
            errorLayout.visibility = View.GONE
            progressBar1.visibility = View.VISIBLE
            mPresenter.GetMovieListByGenre(3, 1)
        }
    }

    override fun error_load_List_2(message: Int) {
        if (errorLayout2.visibility == View.GONE) {
            errorLayout2.visibility = View.VISIBLE
            progressBar2.visibility = View.GONE
            errorText2.text = resources.getString(message)
        }
        errorBtnRetry2.setOnClickListener {
            errorLayout2.visibility = View.GONE
            progressBar2.visibility = View.VISIBLE
            mPresenter.GetMovieListByGenre(2, 2)
        }
    }

    override fun error_load_List_3(message: Int) {
        if (errorLayout3.visibility == View.GONE) {
            errorLayout3.visibility = View.VISIBLE
            progressBar3.visibility = View.GONE
            errorText3.text = resources.getString(message)
        }
        errorBtnRetry3.setOnClickListener {
            errorLayout3.visibility = View.GONE
            progressBar3.visibility = View.VISIBLE
            mPresenter.GetMovieListByGenre(9, 3)
        }
    }

    companion object {
        fun newInstance(): VitrinFragment {
            return VitrinFragment()
        }
    }
}