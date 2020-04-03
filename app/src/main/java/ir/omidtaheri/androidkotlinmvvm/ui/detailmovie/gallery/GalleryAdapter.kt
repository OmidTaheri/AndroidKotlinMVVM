package ir.omidtaheri.androidkotlinmvvm.ui.detailmovie.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseViewHolder
import ir.omidtaheri.androidkotlinmvvm.utils.ViewUtils

class GalleryAdapter(
    val list: MutableList<String>,
    val context: Context,
    var Manager: MyGridAutofitLayoutManager
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val layout: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_gallery, parent, false)
                val imageview =
                    layout.findViewById<ImageView>(R.id.image)
                val layoutParams = imageview.layoutParams
                layoutParams.width = ViewUtils.dpToPx(Manager.getmColumnWidth().toFloat())
                layoutParams.height = ViewUtils.dpToPx(Manager.getmColumnWidth().toFloat())
                imageview.layoutParams = layoutParams
                ViewHolder(
                    layout
                )
            }
            VIEW_TYPE_EMPTY -> EmptyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_empty_view, parent, false)
            )
            else -> EmptyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_empty_view, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return if (list != null && list.size > 0) {
            list.size
        } else {
            1
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (list != null && list.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    fun addItems(repoList: List<String>) {
        list?.addAll(repoList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }

        @BindView(R.id.image)
        lateinit var image: ImageView


        override fun clear() {}
        override fun onBind(position: Int) {
            super.onBind(position)
            val item = list[position]
            val width = ViewUtils.dpToPx(Manager.getmColumnWidth().toFloat())
            val option = RequestOptions()
            option.placeholder(R.drawable.image_placeholder)
            Glide.with(itemView.getContext())
                .load(item)
                .apply(option)
                .into(image)
        }


    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView) {
        init {
            ButterKnife.bind(this, itemView)
        }

        @BindView(R.id.message)
        lateinit var message: TextView

        override fun clear() {}

        override fun onBind(position: Int) {
            super.onBind(position)
            message.text = "موردی وجود ندارد"
        }


    }


}