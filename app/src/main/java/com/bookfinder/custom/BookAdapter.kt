package com.bookfinder.custom

import android.content.Context
import android.graphics.Color
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class BookAdapter(
    var context: Context,
    diffCallback: DiffUtil.ItemCallback<Book.RS.Items>
) : ListAdapter<Book.RS.Items, ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(R.layout.item_book_contents, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).run {
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

class ViewHolder(
    @LayoutRes layoutResId: Int,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
) {
    val ivPicture: ImageView = itemView.findViewById(R.id.iv_picture)
    val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    val tvAuthor: TextView = itemView.findViewById(R.id.tv_author)
    val tvPublishedDate: TextView = itemView.findViewById(R.id.tv_published_Date)
}