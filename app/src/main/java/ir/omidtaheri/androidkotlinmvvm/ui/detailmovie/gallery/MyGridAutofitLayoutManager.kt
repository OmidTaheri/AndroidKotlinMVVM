package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.gallery

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidkotlinmvvm.utils.ScreenUtils
import ir.omidtaheri.androidkotlinmvvm.utils.ViewUtils
import kotlin.math.roundToInt


class MyGridAutofitLayoutManager(
    var context: Context,
    var recyclerView: RecyclerView
) {
    init {
        setColumnWidth()
    }


    private var mColumnWidth = 0
    private var mColumnNumber = 0


    val totalPaddingItems: Int
        get() {
            if (getmColumnNumber() == 2) {
                return 8
            } else if (getmColumnNumber() == 3) {
                return 16
            }
            return 0
        }


    fun setColumnWidth() {

        var totalSpace: Int
        var recyclerViewWidth: Int = recyclerView.getWidth()
        if (recyclerViewWidth == 0) {
            recyclerViewWidth = ScreenUtils.getScreenWidth(context)
        }

        totalSpace =
            recyclerViewWidth - recyclerView.getPaddingRight() - recyclerView.getPaddingLeft()

        totalSpace = ViewUtils.pxToDp(totalSpace.toFloat()).roundToInt()

        if (totalSpace <= 320) {
            mColumnNumber = 2
        } else if (totalSpace in 321..360) {
            mColumnNumber = 2
        } else if (totalSpace in 361..600) {
            mColumnNumber = 3
        } else if (totalSpace > 600) {
            mColumnNumber = 3
        }
        totalSpace -= totalPaddingItems
        if (totalSpace <= 320) {
            mColumnWidth = totalSpace / mColumnNumber
        } else if (totalSpace in 321..360) {
            mColumnWidth = totalSpace / mColumnNumber
        } else if (totalSpace in 361..600) {
            mColumnWidth = totalSpace / mColumnNumber
        } else if (totalSpace > 600) {
            mColumnWidth = totalSpace / mColumnNumber
        }
    }

    fun getmColumnWidth(): Int {
        return mColumnWidth
    }

    fun getmColumnNumber(): Int {
        return mColumnNumber
    }


}