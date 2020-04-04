package ir.omidtaheri.androidkotlinmvvm.ui.main.favorite

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.utils.ScreenUtils.getScreenWidth
import ir.omidtaheri.androidkotlinmvvm.utils.ViewUtils.pxToDp

class MyGridAutofitLayoutManager(
    var context: Context,
    var recyclerView: RecyclerView
) {
    private var mColumnWidth = 0
    private var mColumnNumber = 0
    val totalPaddingItems: Int
        get() {
            if (getmColumnNumber() == 2) {
                return (8 + context.resources
                    .getDimension(R.dimen.material_component_cards_space_between_cards) * 2).toInt()
            } else if (getmColumnNumber() == 3) {
                return (16 + context.resources
                    .getDimension(R.dimen.material_component_cards_space_between_cards) * 2).toInt()
            }
            return 0
        }

    fun setColumnWidth() {
        var totalSpace: Int
        var recyclerViewWidth = recyclerView.width
        if (recyclerViewWidth == 0) {
            recyclerViewWidth = getScreenWidth(context)
        }
        totalSpace =
            recyclerViewWidth - recyclerView.paddingRight - recyclerView.paddingLeft
        totalSpace =
            pxToDp(totalSpace.toFloat()).toInt()
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

    init {
        setColumnWidth()
    }
}