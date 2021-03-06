package ir.omidtaheri.androidkotlinmvvm.ui.splash

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseActivity
import ir.omidtaheri.androidkotlinmvvm.ui.main.MainActivity
import ir.omidtaheri.androidkotlinmvvm.utils.Dialog
import javax.inject.Inject


class SplashActivity : BaseActivity(), SplashMvpView, Dialog.Callback {

    @Inject
    lateinit var mPresenter: SplashMvpPresenter<SplashMvpView>

    @BindView(R.id.app_name)
    lateinit var appName: TextView

    @BindView(R.id.main_progress)
    lateinit var mainProgress: ProgressBar

    @BindView(R.id.errore_text)
    lateinit var erroreText: TextView

    @BindView(R.id.error_btn_retry)
    lateinit var errorBtnRetry: TextView

    @BindView(R.id.error_layout)
    lateinit var errorLayout: ConstraintLayout

    @BindView(R.id.copyright)
    lateinit var copyright: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        getActivityComponent().inject(this)

        setUnBinder(ButterKnife.bind(this))

        mPresenter.onAttach(this)

        setUp()
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun setUp() {
        mainProgress.indeterminateDrawable?.setColorFilter(
            getResources()
                .getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN
        )

        onNegativeClick()
    }


    override fun launchMainActivity() {
        val intent: Intent = MainActivity.getStartIntent(this)
        startActivity(intent)
        finish()
    }

    override fun showErrorLayout() {
        if (errorLayout.getVisibility() === View.GONE) {
            errorLayout.setVisibility(View.VISIBLE)
            mainProgress.visibility = View.GONE
            erroreText.setText(R.string.error_connection)
        }
        errorBtnRetry.setOnClickListener {
            errorLayout.setVisibility(View.GONE)
            mainProgress.visibility = View.VISIBLE
            mPresenter.delayToNextActivity(this)
        }
    }

    override fun onPositiveClick() {

    }

    override fun onNegativeClick() {
        mPresenter.delayToNextActivity(this)
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }
}