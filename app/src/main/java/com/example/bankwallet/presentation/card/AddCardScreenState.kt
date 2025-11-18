package com.example.bankwallet.presentation.card

import androidx.compose.ui.text.input.TextFieldValue
import com.example.bankwallet.domain.model.CardFormValidationResult

/**
 * Holds all form field values
 * Separates form state from UI logic
 */
data class CardFormState(
    val cardName: String = "",
    val cardNumber: String = "",
    val cvv: String = "",
    val expirationDate: TextFieldValue = TextFieldValue(""),
    val ownerName: String = ""
)

/**
 * Holds all form validation errors
 * Separates validation state from form state
 */
data class CardFormErrorState(
    val cardNameError: String? = null,
    val cardNumberError: String? = null,
    val cvvError: String? = null,
    val expirationDateError: String? = null,
    val ownerNameError: String? = null
) {
    companion object {
        fun fromValidationResult(result: CardFormValidationResult): CardFormErrorState =
            CardFormErrorState(
                cardNameError = result.cardNameError,
                cardNumberError = result.cardNumberError,
                cvvError = result.cvvError,
                expirationDateError = result.expirationDateError,
                ownerNameError = result.ownerNameError
            )
    }
}

/**
 * Complete Add Card screen state
 * Combines form, validation and UI state
 */
data class AddCardScreenState(
    val formState: CardFormState = CardFormState(),
    val errorState: CardFormErrorState = CardFormErrorState(),
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false
)

