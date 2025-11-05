package com.example.bankwallet.domain.model

data class Card(
    val id: Int = 0,
    val cardName: String,
    val cardNumber: String,
    val cvv: String,
    val expirationDate: String,
    val bankName: String
)
