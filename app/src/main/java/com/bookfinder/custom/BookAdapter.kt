package com.bookfinder.custom

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bookfinder.R
import com.bookfinder.model.Book
import android.widget.ImageView
import android.widget.TextView
import com.bookfinder.constants.Constants
import com.bookfinder.model.ViewTypeModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.internal.Contexts

/**
 * Book Adapter
 * api에서 전달 받은 책 데이터를 리스트에 그려주기 위한 Adapter
 * ListAdapter 를 통해 내부에서 데이터의 DiffUtil를 사용
 */
class BookAdapter(
    var context: Context,
    diffCallback: DiffUtil.ItemCallback<ViewTypeModel>
) : ListAdapter<ViewTypeModel, ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            Constants.ViewType.BOOK -> {
                BookViewHolder(R.layout.item_book_contents, parent)
            }
            else -> {
                LoadingViewHolder(R.layout.item_book_loading, parent)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is BookViewHolder) {
            (item as Book.RS.Items).run {
                if (this is Book.RS.Items) {
                    try {
                        if (volumeInfo.imageLinks == null) {
                            holder.ivPicture.setImageResource(0)
                        } else {
                            Glide.with(context).load(volumeInfo.imageLinks.thumbnail)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true).into(holder.ivPicture)
                        }

                        holder.tvTitle.text = volumeInfo.title
                        if (volumeInfo.authors == null) {
                            holder.tvAuthor.text = "알 수 없음"
                        } else {
                            holder.tvAuthor.text = if (volumeInfo.authors.size > 1) {
                                "${volumeInfo.authors[0]}(${volumeInfo.authors.size})"
                            } else {
                                volumeInfo.authors[0]
                            }
                        }
                        holder.tvPublishedDate.text = this.volumeInfo.publishedDate
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is Book.RS.Items) {
            Constants.ViewType.BOOK
        } else {
            getItem(position).viewType
        }
    }
}

open class ViewHolder(
    @LayoutRes layoutResId: Int,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
)

class LoadingViewHolder(
    @LayoutRes layoutResId: Int,
    parent: ViewGroup
) : ViewHolder(layoutResId, parent)

class BookViewHolder(
    @LayoutRes layoutResId: Int,
    parent: ViewGroup
) : ViewHolder(layoutResId, parent) {
    val ivPicture: ImageView = itemView.findViewById(R.id.iv_picture)
    val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    val tvAuthor: TextView = itemView.findViewById(R.id.tv_author)
    val tvPublishedDate: TextView = itemView.findViewById(R.id.tv_published_Date)
}