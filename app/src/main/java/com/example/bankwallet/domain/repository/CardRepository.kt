package com.example.bankwallet.domain.repository

import com.example.bankwallet.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun getAllCards(): Flow<List<Card>>
    suspend fun addCard(card: Card)
    suspend fun deleteCard(card: Card)
}