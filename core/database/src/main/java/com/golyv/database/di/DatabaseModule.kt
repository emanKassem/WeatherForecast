package com.golyv.database.di

import android.content.Context
import com.golyv.database.AppDatabase
import com.golyv.database.CapturedImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class DatabaseModule {

    @Provides
    fun provideCapturedImagesDao(@ApplicationContext context: Context): CapturedImageDao =
        AppDatabase.getDatabase(context).capturedImagesDao()
}