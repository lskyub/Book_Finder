package com.bookfinder.di

import android.content.Context
import com.bookfinder.network.ApiService
import com.bookfinder.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        api: ApiService,
    ): BookRepository {
        return BookRepository(context, api)
    }
}