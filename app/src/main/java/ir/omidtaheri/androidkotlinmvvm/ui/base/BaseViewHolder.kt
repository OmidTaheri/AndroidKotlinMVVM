package ir.omidtaheri.androidkotlinmvvm.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    var currentPosition = 0
        private set

    abstract fun clear()

    fun onBind(position: Int) {
        currentPosition = position
        clear()
    }

}