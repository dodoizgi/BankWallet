package com.example.bankwallet.presentation.card

import com.example.bankwallet.domain.model.Card

sealed class CardIntent {
    data object LoadCards : CardIntent()
    data class AddCard(val card: Card) : CardIntent()
    data class DeleteCard(val card: Card) : CardIntent()
}