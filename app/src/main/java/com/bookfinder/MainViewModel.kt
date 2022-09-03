package com.bookfinder

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookfinder.custom.BookScrollListener
import com.bookfinder.custom.SearchTextWatcher
import com.bookfinder.model.Book
import com.bookfinder.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), DefaultLifecycleObserver, View.OnClickListener {
    private val mMaxResult = 20

    private var beforStr = ""

    private val booksList = arrayListOf<Book.RS.Items>()

    private val books: MutableLiveData<List<Book.RS.Items>> = MutableLiveData<List<Book.RS.Items>>()

    private val total: MutableLiveData<String> = MutableLiveData<String>()

    private fun laodBooks(str: String = beforStr, start: Int = booksList.size) {
        if (str.isNullOrEmpty() || str.isNullOrBlank()) {
            total.value = ""
            books.value = booksList
        } else {
            repository.getBookList("$str", mMaxResult, start, {
                booksList.addAll(it.items)
                books.value = booksList
                total.value = it.totalItems
            }, {
                Log.i("book", "${it.message}")
            })
        }

    }

    fun getTotal(): LiveData<String> {
        return total
    }

    fun getBookList(): LiveData<List<Book.RS.Items>> {
        return books
    }

    fun getScrollListener(layoutManager: LinearLayoutManager): BookScrollListener {
        return object : BookScrollListener(layoutManager) {
            override fun onLoadMore() {
                laodBooks()
            }
        }
    }

    val textWatcher = object : SearchTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            beforStr = s?.toString() ?: ""
            booksList.clear()
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_search) {
            booksList.clear()
            laodBooks()
        }
    }
}