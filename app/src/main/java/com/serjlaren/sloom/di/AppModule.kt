package com.serjlaren.sloom.di

import android.content.Context
import androidx.room.Room
import com.serjlaren.sloom.data.database.WordsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @DispatcherIO
    @Singleton
    @Provides
    fun provideDispatcherIO() = Dispatchers.IO

    @DispatcherMain
    @Singleton
    @Provides
    fun provideDispatcherMain() = Dispatchers.Main

    /**
     * Don't cancel this scope!!! Only its child jobs.
     */
    @CoroutineScopeIO
    @Singleton
    @Provides
    fun provideScopeIO(@DispatcherIO ioDispatcher: CoroutineDispatcher) = CoroutineScope(ioDispatcher + SupervisorJob())

    @Singleton
    @Provides
    fun provideWordsDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        WordsDatabase::class.java,
        WordsDatabase.databaseName,
    )
        .fallbackToDestructiveMigration()
        .createFromAsset("")
        .build()

    @Singleton
    @Provides
    fun provideWordsDao(wordsDatabase: WordsDatabase) = wordsDatabase.wordsDao()
}
