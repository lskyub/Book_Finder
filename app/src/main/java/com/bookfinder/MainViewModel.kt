package com.bookfinder

import android.util.Log
import androidx.lifecycle.*
import com.bookfinder.constants.Constants
import com.bookfinder.model.Book
import com.bookfinder.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), DefaultLifecycleObserver {
    private val mMaxResult = 20

    private val books: MutableLiveData<Book.RS> by lazy {
        MutableLiveData<Book.RS>().also {
            laodBooks(0)
        }
    }

    private fun laodBooks(startIndex: Int) {
        repository.getBookList("flowers+inauthor", mMaxResult, startIndex, {
            books.value = it
        }, {
            Log.i("book", "${it.message}")
        })
    }

    fun getBookList(): LiveData<Book.RS> {
        return books
    }

    fun getNextBookList(startIndex: Int) {
        Log.i("sgim", "${startIndex}")
        laodBooks(startIndex)
    }
}