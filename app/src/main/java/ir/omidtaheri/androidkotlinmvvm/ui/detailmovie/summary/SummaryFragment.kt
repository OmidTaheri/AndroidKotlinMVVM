package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.di.component.ActivityComponent
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseFragment
import javax.inject.Inject

class SummaryFragment : BaseFragment(), SummaryMvpView {
    @Inject
    lateinit var mPresenter: SummaryMvpPresenter<SummaryMvpView>

    @BindView(R.id.description)
    lateinit var description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_description, container, false)
        val component: ActivityComponent? = getActivityComponent()
        if (component != null) {
            component.inject(this)
            setUnBinder(ButterKnife.bind(this, v))
            mPresenter.onAttach(this)
        }
        return v
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        val plot: String = getArguments()?.getString("plot", "") ?: ""
        description.text = plot
    }

    override fun setUp(view: View) {}

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()

    }

    companion object {
        fun newInstance(plot: String?): SummaryFragment {
            val fragment = SummaryFragment()
            val args = Bundle()
            args.putString("plot", plot)
            fragment.setArguments(args)
            return fragment
        }
    }
}