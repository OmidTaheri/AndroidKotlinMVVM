package ir.omidtaheri.androidkotlinmvvm.ui.main.vitrin

import ak.sh.ay.oblique.ObliqueView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.data.network.model.Movie
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseViewHolder

class VitrinAdapter(
    private val list: MutableList<Movie>,
    private val context: Context
) : RecyclerView.Adapter<BaseViewHolder>() {

    private lateinit var mCallback: Callback

    fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_vitrin,
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

    fun addItems(repoList: List<Movie>?) {
        list!!.addAll(repoList!!)
        notifyDataSetChanged()
    }

    interface Callback {
        fun onItemClick(movie_id: Int)
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        @BindView(R.id.obliqueView)
        lateinit var obliqueView: ObliqueView

        @BindView(R.id.title_movie)
        lateinit var titleMovie: TextView

        override fun clear() {}

        override fun onBind(position: Int) {
            super.onBind(position)
            val (id, title, poster) = list[position]
            titleMovie.text = title
            Glide.with(itemView.context)
                .load(poster)
                .apply(RequestOptions().placeholder(R.drawable.film_placeholder))
                .into(obliqueView)
            itemView.setOnClickListener { if (mCallback != null) mCallback.onItemClick(id) }
        }

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    inner class EmptyViewHolder(itemView: View?) :
        BaseViewHolder(itemView!!) {
        @BindView(R.id.message)
        lateinit  var message: TextView
         override fun clear() {}
        override fun onBind(position: Int) {
            super.onBind(position)
            message!!.text = "موردی وجود ندارد"
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