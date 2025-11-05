package com.example.bankwallet.data.repository

import com.example.bankwallet.data.local.CardEntity
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun getAllCards(): Flow<List<CardEntity>>
    suspend fun addCard(card: CardEntity)
    suspend fun deleteCard(card: CardEntity)
}