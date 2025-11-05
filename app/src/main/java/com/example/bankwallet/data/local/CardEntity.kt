package com.example.bankwallet.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cardName: String,
    val cardNumber: String,
    val cvv: String,
    val expirationDate: String,
    val bankName: String
)