package com.bookfinder

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookfinder.Utils.hideKeyboard
import com.bookfinder.constants.Constants
import com.bookfinder.custom.BookScrollListener
import com.bookfinder.custom.SearchTextWatcher
import com.bookfinder.model.ViewTypeModel
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

    private val booksList = arrayListOf<ViewTypeModel>()

    private val books: MutableLiveData<List<ViewTypeModel>> = MutableLiveData<List<ViewTypeModel>>()

    private val total: MutableLiveData<String> = MutableLiveData<String>()

    private var isCallApi = false

    private fun laodBooks(str: String = beforStr, start: Int = booksList.size) {
        repository.cancelApi()
        if (str.isNullOrEmpty() || str.isNullOrBlank()) {
            total.value = ""
            books.value = booksList
        } else {
            synchronized(isCallApi) {
                isCallApi = true
                booksList.add(ViewTypeModel(Constants.ViewType.LOADING))
                books.value = booksList
                repository.getBookList("$str", mMaxResult, start, {
                    isCallApi = false
                    booksList.removeAt(booksList.size - 1)
                    booksList.addAll(it.items)
                    books.value = booksList
                    total.value = it.totalItems
                }, {
                    isCallApi = false
                })
            }
        }
    }

    fun getTotal(): LiveData<String> {
        return total
    }

    fun getBookList(): LiveData<List<ViewTypeModel>> {
        return books
    }

    fun getScrollListener(layoutManager: LinearLayoutManager): BookScrollListener {
        return object : BookScrollListener(layoutManager) {
            override fun onLoadMore() {
                if (!isCallApi) {
                    laodBooks()
                }
            }
        }
    }

    val textWatcher = object : SearchTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            beforStr = s?.toString() ?: ""
            booksList.clear()
            laodBooks()
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_search) {
            v.hideKeyboard()
            booksList.clear()
            laodBooks()
        }
    }
}