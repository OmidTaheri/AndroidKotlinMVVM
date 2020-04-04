package ir.omidtaheri.androidkotlinmvvm.ui.main.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.data.network.model.GenresListResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseViewHolder

class CategoryAdapter(
    private val list: MutableList<GenresListResponse>,
    private val context: Context
) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var mCallback: Callback

    fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_genre,
                    parent,
                    false
                )
            )
            VIEW_TYPE_EMPTY -> EmptyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_empty_view,
                    parent,
                    false
                )
            )
            else -> EmptyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_empty_view,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return if ( list.size > 0) {
            list.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ( list.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    fun addItems(repoList: List<GenresListResponse>?) {
        list.addAll(repoList!!)
        notifyDataSetChanged()
    }

    interface Callback {
        fun onItemClick(item: GenresListResponse?)
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        @BindView(R.id.category_name)
        lateinit var categoryName: TextView
        override fun clear() {}
        override fun onBind(position: Int) {
            super.onBind(position)
            val item = list[position]
            categoryName.text = item.name
            itemView.setOnClickListener { mCallback.onItemClick(item) }
        }

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    inner class EmptyViewHolder(itemView: View) :
        BaseViewHolder(itemView) {
        @BindView(R.id.message)
        lateinit var message: TextView
        override fun clear() {}
        override fun onBind(position: Int) {
            super.onBind(position)
            message.text = "موردی وجود ندارد"
        }

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
    }

}