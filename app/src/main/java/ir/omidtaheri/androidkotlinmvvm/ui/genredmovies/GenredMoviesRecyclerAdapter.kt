package ir.omidtaheri.androidkotlinmvvm.ui.genredmovies

import ak.sh.ay.oblique.ObliqueView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ir.omidtaheri.androidkotlinmvvm.R
import ir.omidtaheri.androidkotlinmvvm.data.network.model.Movie
import ir.omidtaheri.androidkotlinmvvm.ui.base.BaseViewHolder
import ir.omidtaheri.androidkotlinmvvm.utils.ViewUtils

class GenredMoviesRecyclerAdapter(
    val list: MutableList<Movie>,
    val context: Context,
    var Manager: MyGridAutofitLayoutManager
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
        const val LOADING = 2
    }

    lateinit var mCallback: Callback

    private var isLoadingAdded = false
    private var retryPageLoad = false
    private lateinit var errorMsg: String

    var errored_category_id = 0
    var errored_page = 0


    fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val layout: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_vitrin, parent, false)
                val Card: CardView = layout.findViewById(R.id.parent)
                Card.layoutParams = FrameLayout.LayoutParams(
                    ViewUtils.dpToPx(Manager.getmColumnWidth().toFloat()) ,WRAP_CONTENT
                            //CardView.LayoutParams.WRAP_CONTENT
                )
                ViewHolder(
                    layout
                )
            }
            LOADING -> LoadingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress, parent, false)
            )
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
        return if (list != null && list.size != 0) {
            list.size
        } else {
            1
        }
    }

    fun addItems(repoList: List<Movie>) {
        list.addAll(repoList)
        notifyDataSetChanged()
    }

    interface Callback {
        fun onItemClick(movie_id: Int)
        fun retryPageLoad(genre_id: Int, page: Int)
    }

    override fun getItemViewType(position: Int): Int {
        return if (list != null && list.size > 0) {
            if (position == list.size - 1 && isLoadingAdded) LOADING else VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */
    fun add(mc: Movie) {
        list.add(mc)
        notifyItemInserted(list.size - 1)
    }

    fun addAll(List: List<Movie>) {
        for (item in List) {
            add(item)
        }
    }

    fun remove(item: Movie) {
        val position = list.indexOf(item)
        if (position > -1) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    val isEmpty: Boolean
        get() = itemCount == 0

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Movie())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = list!!.size - 1
        val item = getItem(position)
        if (item != null) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): Movie {
        return list!![position]
    }

    fun showRetry(
        show: Boolean,
        errorMsg: String?,
        category_id: Int,
        page: Int
    ) {
        errored_category_id = category_id
        errored_page = page
        retryPageLoad = show
        notifyItemChanged(list!!.size - 1)
        if (errorMsg != null) this.errorMsg = errorMsg
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
        lateinit var message: TextView
         override fun clear() {}
        override fun onBind(position: Int) {
            super.onBind(position)
            message.text = "موردی وجود ندارد"
        }

        init {
            ButterKnife.bind(this, itemView!!)
        }
    }

    inner class LoadingViewHolder(itemView: View?) :
        BaseViewHolder(itemView!!), View.OnClickListener {
        @BindView(R.id.loadmore_progress)
        lateinit  var loadmoreProgress: ProgressBar

        @BindView(R.id.loadmore_retry)
        lateinit var loadmoreRetry: Button

        @BindView(R.id.loadmore_errortxt)
        lateinit var loadmoreErrortxt: TextView

        @BindView(R.id.loadmore_errorlayout)
        lateinit var loadmoreErrorlayout: ConstraintLayout

         override fun clear() {}
        override fun onBind(position: Int) {
            super.onBind(position)
            if (retryPageLoad) {
                loadmoreErrorlayout!!.visibility = View.VISIBLE
                loadmoreProgress!!.visibility = View.GONE
                if (errorMsg!!.length != 0) {
                    loadmoreErrortxt!!.text = errorMsg
                } else {
                    loadmoreErrortxt!!.setText(R.string.error_load_page)
                }
            } else {
                loadmoreErrorlayout!!.visibility = View.GONE
                loadmoreProgress!!.visibility = View.VISIBLE
            }
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.loadmore_retry -> {
                    mCallback!!.retryPageLoad(errored_category_id, errored_page)
                    showRetry(false, null, 0, 0)
                }
            }
        }

        init {
            ButterKnife.bind(this, itemView!!)
            loadmoreRetry!!.setOnClickListener(this)
        }
    }


}