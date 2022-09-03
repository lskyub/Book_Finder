package com.bookfinder

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bookfinder.custom.BookAdapter
import com.bookfinder.custom.BookDecoration
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
            addOnScrollListener(viewModel.getScrollListener(layoutManager as LinearLayoutManager))
        }

        findViewById<AppCompatEditText>(R.id.et_book).apply {
            addTextChangedListener(viewModel.textWatcher)
        }

        findViewById<AppCompatImageView>(R.id.btn_search).setOnClickListener(viewModel)

        viewModel.getTotal().observe(this) {
            results.text = "Results : $it"

        }
        viewModel.getBookList().observe(this) {
            (rvBooks.adapter as BookAdapter).run {
                submitList(it.toMutableList())
            }
        }
    }

    private val differ: DiffUtil.ItemCallback<Book.RS.Items> =
        object : DiffUtil.ItemCallback<Book.RS.Items>() {
            override fun areItemsTheSame(
                oldItem: Book.RS.Items,
                newItem: Book.RS.Items
            ): Boolean {
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