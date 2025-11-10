package com.example.bankwallet.data.repository

import com.example.bankwallet.data.local.CardDao
import com.example.bankwallet.data.local.CardEntity
import com.example.bankwallet.domain.repository.CardRepository
import com.example.bankwallet.domain.model.Card
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val cardDao: CardDao
) : CardRepository {
    override fun getAllCards(): Flow<List<Card>> {
        return cardDao.getAllCards().map { list ->
            list.map { entity ->
                Card(
                    id = entity.id,
                    cardName = entity.cardName,
                    cardNumber = entity.cardNumber,
                    cvv = entity.cvv,
                    expirationDate = entity.expirationDate,
                )
            }
        }
    }

    override suspend fun addCard(card: Card) {
        cardDao.insertCard(CardEntity(card.id, card.cardName, card.cardNumber, card.cvv, card.expirationDate))
    }

    override suspend fun deleteCard(card: Card) {
        cardDao.deleteCard(CardEntity(card.id, card.cardName, card.cardNumber, card.cvv, card.expirationDate))
    }
}