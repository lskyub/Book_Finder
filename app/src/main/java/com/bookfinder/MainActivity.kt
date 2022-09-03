package com.bookfinder

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bookfinder.custom.BookAdapter
import com.bookfinder.custom.BookDecoration
import com.bookfinder.custom.BookScrollListener
import com.bookfinder.model.Book
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val results = findViewById<TextView>(R.id.tv_results)
        val rvBooks = findViewById<RecyclerView>(R.id.rv_books).apply {
            adapter = BookAdapter(context, differ)
            addItemDecoration(BookDecoration(20))
            addOnScrollListener(object : BookScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore() {
                    val listCount = (adapter as BookAdapter).itemCount
                    viewModel.getNextBookList(listCount)
                }
            })
        }

        viewModel.getBookList().observe(this) {
            results.text = "Results : ${it.totalItems}"
            (rvBooks.adapter as BookAdapter)?.run {
                if (this.itemCount != 0) {
                    submitList(arrayListOf<Book.RS.Items>().apply {
                        addAll(currentList)
                        addAll(it.items)
                    })
                } else {
                    submitList(it.items)
                }
            }
        }
    }

    private val differ: DiffUtil.ItemCallback<Book.RS.Items> =
        object : DiffUtil.ItemCallback<Book.RS.Items>() {
            override fun areItemsTheSame(oldItem: Book.RS.Items, newItem: Book.RS.Items): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Book.RS.Items,
                newItem: Book.RS.Items
            ): Boolean {
                return oldItem.toString() == newItem.toString()
            }
        }
}