package ir.omidtaheri.androidkotlinmvvm.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import ir.omidtaheri.androidkotlinmvvm.utils.CommonUtils


abstract class BaseFragment : Fragment(), MvpView {

    private var mActivity: BaseActivity? = null
    private lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context
            mActivity = activity
            activity.onFragmentAttached()
        }
    }

    override fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this.getContext())
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing) {
            mProgressDialog.cancel()
        }
    }

    override fun onError(message: String?) {
        mActivity?.onError(message)
    }

    override fun onError(resId: Int) {
        mActivity?.onError(resId)
    }

    override fun showMessage(message: String?) {
        mActivity?.showMessage(message)
    }

    override fun showMessage(resId: Int) {
        mActivity?.showMessage(resId)
    }

    override fun isNetworkConnected(): Boolean {
        return mActivity?.isNetworkConnected() ?: false
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun hideKeyboard() {
        mActivity?.hideKeyboard()
    }


    protected abstract fun setUp(view: View)

    override fun onDestroy() {

        super.onDestroy()
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}