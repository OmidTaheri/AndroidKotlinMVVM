package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.di.component.ActivityComponent
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseFragment
import javax.inject.Inject


class GalleryFragment : BaseFragment(), GalleryMvpView {
    @Inject
    lateinit var mPresenter: GalleryMvpPresenter<GalleryMvpView>

    @BindView(R.id.image_list)
    lateinit var imageList: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_gallery, container, false)

        val component: ActivityComponent? = getActivityComponent()
        if (component != null) {
            component.inject(this)
            setUnBinder(ButterKnife.bind(this, v))
            mPresenter.onAttach(this)
        }

        return v
    }

    override fun setUp(view: View) {
        val list: MutableList<String>? =
            getArguments()?.getStringArrayList("image_urls")

        //////////////////////////////
        val manager =
            MyGridAutofitLayoutManager(mActivity.baseContext, imageList)
        val layoutManager: GridLayoutManager
        layoutManager = GridLayoutManager(mActivity.baseContext, manager.getmColumnNumber())
        imageList.setLayoutManager(layoutManager)
        //////////////
        val adapter = GalleryAdapter(list!!, mActivity.baseContext, manager)
        imageList.setAdapter(adapter)
        layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    GalleryAdapter.VIEW_TYPE_EMPTY -> layoutManager.getSpanCount()
                    GalleryAdapter.VIEW_TYPE_NORMAL -> 1
                    else -> 1
                }
            }
        })
    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

    companion object {
        fun newInstance(image_urls: List<String>): GalleryFragment {
            val fragment = GalleryFragment()
            val args = Bundle()
            args.putStringArrayList(
                "image_urls",
                ArrayList(image_urls)
            )
            fragment.setArguments(args)
            return fragment
        }
    }
}