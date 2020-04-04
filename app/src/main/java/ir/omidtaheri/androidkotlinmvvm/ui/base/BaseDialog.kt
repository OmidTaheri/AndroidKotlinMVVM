package ir.omidtaheri.androidkotlinmvvm.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import butterknife.Unbinder
import ir.omidtaheri.androidkotlinmvvm.di.component.ActivityComponent

abstract class BaseDialog : DialogFragment(), DialogMvpView {

    lateinit var mActivity: BaseActivity
    private lateinit var mUnBinder: Unbinder


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mActivity = context
            mActivity.onFragmentAttached()
        }
    }

    override fun showLoading() {
        mActivity.showLoading()
    }

    override fun hideLoading() {

        mActivity.hideLoading()

    }

    override fun onError(message: String?) {

        mActivity?.onError(message)

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


    open fun getBaseActivity(): BaseActivity {
        return mActivity
    }

    open fun getActivityComponent(): ActivityComponent? {
        return mActivity.getActivityComponent()

    }

    open fun setUnBinder(unBinder: Unbinder) {
        mUnBinder = unBinder
    }


    protected abstract fun setUp(view: View)


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = RelativeLayout(getActivity())
        root.setLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        // creating the fullscreen dialog
        val dialog = Dialog(this.getContext()!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)

        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.getWindow()?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
    }

    override fun show(fragmentManager: FragmentManager, tag: String?) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val prevFragment: Fragment? = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    override fun dismissDialog(tag: String) {
        dismiss()
        mActivity.onFragmentDetached(tag)
    }

    override fun onDestroy() {

        mUnBinder.unbind()

        super.onDestroy()
    }
}