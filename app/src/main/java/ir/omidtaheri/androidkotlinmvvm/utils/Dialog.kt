package ir.omidtaheri.androidkotlinmvvm.utils

import android.app.Activity
import androidx.annotation.NonNull
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.GravityEnum
import com.afollestad.materialdialogs.MaterialDialog
import ir.omidtaheri.androidkotlinmvvm.R

object Dialog {

    fun Show_Update_Dialog(

        appcontext: Activity,
        callback: Callback?
    ): MaterialDialog {

        val Dialog: MaterialDialog = MaterialDialog.Builder(appcontext)
            .title(appcontext.resources.getString(R.string.Dialog_title))
            .content(appcontext.resources.getString(R.string.Dialog_content))
            .contentColorRes(R.color.darkPrimaryText)
            .iconRes(R.mipmap.ic_launcher)
            .backgroundColorRes(R.color.white)
            .dividerColorRes(R.color.darkDividers)
            .negativeColorRes(R.color.colorAccent)
            .positiveColorRes(R.color.colorAccent)
            .titleColorRes(R.color.darkPrimaryText)
            .titleGravity(GravityEnum.CENTER)
            .typeface("byekan_bold.ttf", "byekan.ttf")
            .positiveText("بله")
            .negativeText("خیر")
            .contentGravity(GravityEnum.END)
            .cancelable(false)
            .onPositive { dialog, which -> callback?.onPositiveClick() }
            .onNegative { dialog, which -> callback?.onNegativeClick() }
            .build()
        Dialog.show()
        return Dialog
    }

    interface Callback {
        fun onPositiveClick()
        fun onNegativeClick()
    }
}