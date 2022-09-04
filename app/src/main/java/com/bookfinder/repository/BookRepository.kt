package com.bookfinder.repository

import android.content.Context
import com.bookfinder.model.Book
import com.bookfinder.network.ApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class BookRepository @Inject constructor(
    @ApplicationContext
    val applicationContext: Context,
    private val api: ApiService
) {
    var compositeDisposable = CompositeDisposable()
    fun getBookList(
        str: String,
        maxResult: Int,
        startIndex: Int,
        result: Consumer<Book.RS>,
        error: Consumer<Throwable>
    ) {
        compositeDisposable.add(
            api.search(str, maxResult, startIndex).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(result, error)
        )
    }

    fun cancelApi() {
        compositeDisposable.clear()
    }
}
