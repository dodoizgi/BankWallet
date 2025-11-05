package com.example.bankwallet.presentation.card

import com.example.bankwallet.domain.model.Card

data class CardState(
    val isLoading: Boolean = false,
    val cards: List<Card> = emptyList(),
    val error: String? = null
)
