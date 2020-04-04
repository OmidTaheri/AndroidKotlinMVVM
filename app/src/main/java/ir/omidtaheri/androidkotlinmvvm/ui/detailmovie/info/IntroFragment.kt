package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.info

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

class IntroFragment : BaseFragment(), IntroMvpView {
    @Inject
    lateinit var mPresenter: IntroMvpPresenter<IntroMvpView>

    @BindView(R.id.director)
    lateinit var director: TextView

    @BindView(R.id.writer)
    lateinit var writer: TextView

    @BindView(R.id.year)
    lateinit var year: TextView

    @BindView(R.id.country)
    lateinit var country: TextView

    @BindView(R.id.actors)
    lateinit var actors: TextView

    @BindView(R.id.awards)
    lateinit var awards: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_intro, container, false)

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
        val directorText: String = getArguments()?.getString("director", "") ?: ""
        val writerText: String = getArguments()?.getString("writer", "") ?: ""
        val yearText: String = getArguments()?.getString("year", "") ?: ""
        val countryText: String = getArguments()?.getString("country", "") ?: ""
        val actorsText: String = getArguments()?.getString("actors", "") ?: ""
        val awardsText: String = getArguments()?.getString("awards", "") ?: ""
        director.text = directorText
        writer.text = writerText
        year.text = yearText
        country.text = countryText
        actors.text = actorsText
        awards.text = awardsText
    }

    override fun setUp(view: View) {

    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()

    }

    companion object {
        fun newInstance(
            director: String,
            writer: String,
            year: String,
            country: String,
            actors: String,
            awards: String
        ): IntroFragment {
            val fragment = IntroFragment()
            val args = Bundle()
            args.putString("director", director)
            args.putString("writer", writer)
            args.putString("year", year)
            args.putString("country", country)
            args.putString("actors", actors)
            args.putString("awards", awards)
            fragment.setArguments(args)
            return fragment
        }
    }
}