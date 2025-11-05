package com.example.bankwallet.presentation.card

sealed class CardEffect {
    data class ShowToast(val message: String) : CardEffect()
}