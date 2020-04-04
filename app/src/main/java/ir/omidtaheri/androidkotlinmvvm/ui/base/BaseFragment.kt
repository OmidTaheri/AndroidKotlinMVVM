package ir.omidtaheri.androidkotlinmvvm.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import butterknife.Unbinder
import ir.omidtaheri.androidkotlinmvvm.di.component.ActivityComponent
import ir.omidtaheri.androidkotlinmvvm.utils.CommonUtils


abstract class BaseFragment : Fragment(), MvpView {

    lateinit var mActivity: BaseActivity
    lateinit var mProgressDialog: ProgressDialog
    private lateinit var mUnBinder: Unbinder

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
        if (::mProgressDialog.isInitialized && mProgressDialog.isShowing) {
            mProgressDialog.cancel()
        }
    }

    override fun onError(message: String?) {
        mActivity.onError(message)
    }

    override fun onError(resId: Int) {
        mActivity.onError(resId)
    }

    override fun showMessage(message: String?) {
        mActivity.showMessage(message)
    }

    override fun showMessage(resId: Int) {
        mActivity.showMessage(resId)
    }

    override fun isNetworkConnected(): Boolean {
        return mActivity.isNetworkConnected()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun hideKeyboard() {
        mActivity.hideKeyboard()
    }


    open fun getActivityComponent(): ActivityComponent? {
        return mActivity.getActivityComponent()

    }

    open fun getBaseActivity(): BaseActivity? {
        return mActivity
    }

    open fun setUnBinder(unBinder: Unbinder) {
        mUnBinder = unBinder
    }

    protected abstract fun setUp(view: View)

    override fun onDestroy() {
        mUnBinder.unbind()

        super.onDestroy()
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}