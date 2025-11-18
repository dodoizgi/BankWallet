package com.example.bankwallet.domain.usecase

import com.example.bankwallet.domain.model.CardFormValidationResult
import javax.inject.Inject

/**
 * Use Case for validating card form fields
 * Handles all validation logic according to business rules
 */
class ValidateCardUseCase @Inject constructor() {

    fun validateCardName(name: String): String? = when {
        name.isBlank() -> "Kart adı boş olamaz"
        name.length > 30 -> "Kart adı 30 karakterden uzun olamaz"
        else -> null
    }

    fun validateCardNumber(number: String): String? = when {
        number.isBlank() -> "Kart numarası boş olamaz"
        !number.all { it.isDigit() } -> "Sadece rakam giriniz"
        number.length !in 15..16 -> "Kart numarası 15 veya 16 haneli olmalıdır"
        else -> null
    }

    fun validateCvv(cvv: String): String? = when {
        cvv.isBlank() -> "CVV boş olamaz"
        !cvv.all { it.isDigit() } -> "Sadece rakam giriniz"
        cvv.length !in 3..4 -> "CVV 3 veya 4 haneli olmalıdır"
        else -> null
    }

    fun validateExpirationDate(date: String): String? = when {
        date.isBlank() -> "Son kullanma tarihi boş olamaz"
        !date.matches(Regex("^(0[1-9]|1[0-2])/([0-9]{2})$")) -> "Geçerli bir tarih giriniz (AA/YY)"
        else -> null
    }

    fun validateOwnerName(name: String): String? = when {
        name.isBlank() -> "Kart sahibi adı boş olamaz"
        name.length > 60 -> "Kart sahibi adı 60 karakterden uzun olamaz"
        else -> null
    }

    /**
     * Validates all card form fields at once
     * @return CardFormValidationResult containing all validation errors
     */
    operator fun invoke(
        cardName: String,
        cardNumber: String,
        cvv: String,
        expirationDate: String,
        ownerName: String
    ): CardFormValidationResult = CardFormValidationResult(
        cardNameError = validateCardName(cardName),
        cardNumberError = validateCardNumber(cardNumber),
        cvvError = validateCvv(cvv),
        expirationDateError = validateExpirationDate(expirationDate),
        ownerNameError = validateOwnerName(ownerName)
    )
}

