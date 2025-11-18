package com.example.bankwallet.domain.model

/**
 * Data class holding all card form validation errors
 */
data class CardFormValidationResult(
    val cardNameError: String? = null,
    val cardNumberError: String? = null,
    val cvvError: String? = null,
    val expirationDateError: String? = null,
    val ownerNameError: String? = null
) {
    /**
     * Check if all fields are valid
     */
    val isValid: Boolean
        get() = cardNameError == null &&
                cardNumberError == null &&
                cvvError == null &&
                expirationDateError == null &&
                ownerNameError == null

    /**
     * Check if any field has error
     */
    val hasError: Boolean
        get() = !isValid
}

