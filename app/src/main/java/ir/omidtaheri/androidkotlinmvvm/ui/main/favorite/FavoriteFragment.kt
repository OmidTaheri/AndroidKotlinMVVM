package ir.omidtaheri.androidkotlinmvvm.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.data.network.model.DetailMovieResponse
import ir.omidtaheri.androidkotlinmvvm.di.component.ActivityComponent
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseFragment
import ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.DetailMovieActivity
import javax.inject.Inject

class FavoriteFragment : BaseFragment(), FavoriteMvpView,
    FavoriteAdapter.Callback {

    lateinit var mPresenter: FavoriteMvpPresenter<FavoriteMvpView>

    @BindView(R.id.favorit_list)
    lateinit var favoritList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(
            R.layout.fragment_favorite,
            container,
            false)


        return v
    }

    override fun setUp(view: View) {
        mPresenter.getFavoriteList()
    }

    override fun onDestroyView() {
        mPresenter!!.onDetach()
        super.onDestroyView()
    }

    override fun onItemClick(movie_id: Int) {
        mPresenter!!.showMovieDetailActivity(movie_id)
    }

    override fun setFavoriteList(list: List<DetailMovieResponse>) {

        //////////////////////////////
        val manager =
            MyGridAutofitLayoutManager(
                requireContext(),
                favoritList!!
            )
        val layoutManager: GridLayoutManager
        layoutManager = GridLayoutManager( requireContext(), manager.getmColumnNumber())
        favoritList!!.layoutManager = layoutManager
        //////////
        val adapter = FavoriteAdapter(list.toMutableList(),  requireContext(), manager)
        adapter.setCallback(this)
        favoritList!!.adapter = adapter
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    FavoriteAdapter.VIEW_TYPE_EMPTY -> layoutManager.spanCount
                    FavoriteAdapter.VIEW_TYPE_NORMAL -> 1
                    else -> 1
                }
            }
        }
    }

    override fun showMovieDetailActivity(movie_id: Int) {
        val intent = DetailMovieActivity.getStartIntent(
           requireContext(),
            movie_id,
            this.javaClass.simpleName
        )
        startActivity(intent)
       mActivity?.finish()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
        }
    }

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }
}