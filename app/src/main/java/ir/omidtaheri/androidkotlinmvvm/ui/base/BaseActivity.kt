package ir.omidtaheri.androidkotlinmvvm.ui.base


import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
 import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.utils.CommonUtils
import ir.omidtaheri.androidkotlinmvvm.utils.NetworkUtils

abstract class BaseActivity : AppCompatActivity() , MvpView ,  BaseFragment.Callback {


    private lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) === PackageManager.PERMISSION_GRANTED
    }


    override fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing) {
            mProgressDialog.cancel()
        }
    }


    fun showSnackBar(message: String?) {
        val snackbar: Snackbar = Snackbar.make(
            findViewById(R.id.content),
            message.toString(), Snackbar.LENGTH_LONG
        )
        val sbView: View = snackbar.getView()
        val textView = sbView
            .findViewById<View>(R.id.snackbar_text) as TextView
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        textView.gravity = Gravity.RIGHT
        textView.textAlignment = View.TEXT_ALIGNMENT_GRAVITY
        snackbar.show()
    }

    override fun onError(resId: Int) {
        onError(getString(resId))
    }

    override fun onError(message: String?) {
        if (message != null) {
            showSnackBar(message)
        } else {
            showSnackBar(getString(R.string.some_error))
        }
    }

    override fun showMessage(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_LONG).show()
        }
    }

    override fun showMessage(resId: Int) {
        showMessage(getString(resId))
    }

    override fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext)
    }

    override fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

      abstract fun setUp()
}
