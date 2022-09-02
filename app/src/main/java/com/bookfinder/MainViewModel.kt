package com.bookfinder

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bookfinder.constants.Constants
import com.bookfinder.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), DefaultLifecycleObserver {

    fun getBookList() {
        repository.getBookList(Constants.GOOGLE_KEY, {
            Log.i("book", "$it")
        }, {
            Log.i("book", "${it.message}")
        })
    }
}