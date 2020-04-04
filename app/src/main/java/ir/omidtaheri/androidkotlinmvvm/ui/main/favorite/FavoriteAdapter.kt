package ir.omidtaheri.androidkotlinmvvm.ui.main.favorite

import ak.sh.ay.oblique.ObliqueView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.data.network.model.DetailMovieResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseViewHolder
import ir.omidtaheri.androidkotlinmvvm.utils.ViewUtils

class FavoriteAdapter(
    private val list: MutableList<DetailMovieResponse>,
    private val context: Context,

    var Manager: MyGridAutofitLayoutManager
) : RecyclerView.Adapter<BaseViewHolder>() {

    private lateinit  var mCallback: Callback

    fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val layout: View =
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.list_item_favorite,
                        parent,
                        false
                    )
                val Card: CardView =
                    layout.findViewById(R.id.parent)
                Card.layoutParams = FrameLayout.LayoutParams(
                    ViewUtils.dpToPx(Manager.getmColumnWidth().toFloat()) ,WRAP_CONTENT
                  //  CardView.LayoutParams.WRAP_CONTENT
                )
                ViewHolder(
                    layout
                )
            }
            VIEW_TYPE_EMPTY -> EmptyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_empty_view_favorite,
                    parent,
                    false
                )
            )
            else -> EmptyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_empty_view_favorite,
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

    fun addItems(repoList: List<DetailMovieResponse>?) {
        list!!.addAll(repoList!!)
        notifyDataSetChanged()
    }

    interface Callback {
        fun onItemClick(movie_id: Int)
    }

    inner class ViewHolder(itemView: View?) : BaseViewHolder(itemView!!) {
        @BindView(R.id.obliqueView)
        var obliqueView: ObliqueView? = null

        @BindView(R.id.title_movie)
        var titleMovie: TextView? = null
         override fun clear() {}
        override fun onBind(position: Int) {
            super.onBind(position)
            val (id, title, poster) = list!![position]
            titleMovie!!.text = title
            Glide.with(itemView.context)
                .load(poster)
                .apply(RequestOptions().placeholder(R.drawable.film_placeholder))
                .into(obliqueView!!)
            itemView.setOnClickListener { if (mCallback != null) mCallback!!.onItemClick(id) }
        }

        init {
            ButterKnife.bind(this, itemView!!)
        }
    }

    inner class EmptyViewHolder(itemView: View?) :
        BaseViewHolder(itemView!!) {
        @BindView(R.id.message)
        var message: TextView? = null
         override fun clear() {}
        override fun onBind(position: Int) {
            super.onBind(position)
            message!!.text = "آیتم برگزیده وجود ندارد"
        }

        init {
            ButterKnife.bind(this, itemView!!)
        }
    }

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
    }

}