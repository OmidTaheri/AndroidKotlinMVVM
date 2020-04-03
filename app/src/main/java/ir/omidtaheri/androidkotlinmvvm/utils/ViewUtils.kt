package ir.omidtaheri.androidkotlinmvvm.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import ir.omidtaheri.androidkotlinmvvm.R

object ViewUtils {
    fun pxToDp(px: Float): Float {
        val densityDpi =
            Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / 160f)
    }

    fun dpToPx(dp: Float): Int {
        val density =
            Resources.getSystem().displayMetrics.density
        return Math.round(dp * density)
    }

    fun changeIconDrawableToGray(
        context: Context?,
        drawable: Drawable?
    ) {
        if (drawable != null) {
            drawable.mutate()
            drawable.setColorFilter(
                ContextCompat
                    .getColor(context!!, R.color.green), PorterDuff.Mode.SRC_ATOP
            )
        }
    }
}