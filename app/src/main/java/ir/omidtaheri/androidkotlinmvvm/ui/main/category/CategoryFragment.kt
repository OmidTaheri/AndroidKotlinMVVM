package ir.omidtaheri.androidkotlinmvvm.ui.main.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.data.network.model.GenresListResponse
import ir.omidtaheri.androidkotlinmvvm.di.component.ActivityComponent
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseFragment
import ir.omidtaheri.androidkotlinmvvm.ui.genredmovies.GenredMoviesActivity
import javax.inject.Inject

class CategoryFragment : BaseFragment(), CategoryMvpView,
    CategoryAdapter.Callback {

    @Inject
    lateinit var mPresenter: CategoryMvpPresenter<CategoryMvpView>

    @BindView(R.id.genre_list)
    lateinit var genreList: RecyclerView

    @BindView(R.id.category_progressBar)
    lateinit var categoryProgressBar: ProgressBar

    @BindView(R.id.error_text)
    lateinit var erroreText: TextView

    @BindView(R.id.error_btn_retry)
    lateinit var errorBtnRetry: TextView

    @BindView(R.id.error_layout)
    lateinit var errorLayout: ConstraintLayout



    ////////////
    lateinit  var items: List<GenresListResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_category, container, false)

        val component: ActivityComponent? = getActivityComponent()
        if (component != null) {
            component.inject(this)
            setUnBinder(ButterKnife.bind(this, v))
            mPresenter.onAttach(this)
        }



        return v
    }

    override fun setUp(view: View) {
        if (!::items.isInitialized) {
            mPresenter.getGenreList()
        } else {
            setListGenre(items)
        }
    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

    override fun setListGenre(list: List<GenresListResponse>) {
        items = list
        val adapter = CategoryAdapter(list.toMutableList(), requireContext())
        genreList.adapter = adapter
        adapter.setCallback(this)
        genreList.layoutManager =
            LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
    }

    override fun showGenreDetailActivity(
        genre_id: Int,
        genre_name: String
    ) {
        val intent: Intent =
            GenredMoviesActivity.getStartIntent( requireContext(), genre_id, genre_name)
        startActivity(intent)
    }

    override fun onItemClick(item: GenresListResponse?) {
        mPresenter.showGenreDetailActivity(item!!.id, item.name)
    }

    override fun visibility_progressBar(show: Boolean) {
        if (show) {
            categoryProgressBar.visibility = View.VISIBLE
        } else {
            categoryProgressBar.visibility = View.GONE
        }
    }

    override fun error_load_List(message: Int) {
        if (errorLayout.visibility == View.GONE) {
            errorLayout.visibility = View.VISIBLE
            categoryProgressBar.visibility = View.GONE
            erroreText.text = resources.getString(message)
        }
        errorBtnRetry.setOnClickListener {
            errorLayout.visibility = View.GONE
            categoryProgressBar.visibility = View.VISIBLE
            mPresenter.getGenreList()
        }
    }

    companion object {
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }
}