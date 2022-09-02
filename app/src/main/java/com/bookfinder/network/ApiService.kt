package com.bookfinder.network

import com.bookfinder.constants.Constants
import com.bookfinder.model.Book
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("books/v1/volumes?q=flowers+inauthor:keyes")
    fun search(
        @Query("key") key: String = Constants.GOOGLE_KEY
    ): Observable<Book.RS>

}