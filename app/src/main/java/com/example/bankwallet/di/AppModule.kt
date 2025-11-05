package com.example.bankwallet.di

import android.app.Application
import androidx.room.Room
import com.example.bankwallet.data.local.AppDatabase
import com.example.bankwallet.data.local.CardDao
import com.example.bankwallet.data.repository.CardRepositoryImpl
import com.example.bankwallet.domain.repository.CardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "card_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCardDao(db: AppDatabase) = db.cardDao()

    @Provides
    fun provideRepository(dao: CardDao): CardRepository = CardRepositoryImpl(dao)
}