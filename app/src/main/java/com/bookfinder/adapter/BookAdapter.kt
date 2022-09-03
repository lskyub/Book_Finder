package com.bookfinder.adapter

import android.content.Context
import android.net.Uri
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

class BookAdapter(
    var context: Context,
    diffCallback: DiffUtil.ItemCallback<Book.RS.Items>
) : ListAdapter<Book.RS.Items, ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(R.layout.item_book_contents, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).run {
            this.volumeInfo.imageLinks.thumbnail.run {
                Glide.with(context).load(this).into(holder.ivPicture)
            }
            holder.tvTitle.text = this.volumeInfo.title
            holder.tvAuthor.text = if (this.volumeInfo.authors.size > 1) {
                "${this.volumeInfo.authors[0]}(${this.volumeInfo.authors.size})"
            } else {
                this.volumeInfo.authors[0]
            }
            holder.tvPublishedDate.text = this.volumeInfo.publishedDate
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