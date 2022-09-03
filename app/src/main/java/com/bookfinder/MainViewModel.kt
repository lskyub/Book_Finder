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
    private val books: MutableLiveData<Book.RS> by lazy {
        MutableLiveData<Book.RS>().also {
            laodBooks()
        }
    }

    private fun laodBooks() {
        repository.getBookList(Constants.GOOGLE_KEY, {
            var total = it.totalItems
            books.value = it
        }, {
            Log.i("book", "${it.message}")
        })
    }

    fun getBookList(): LiveData<Book.RS> {
        return books
    }
}