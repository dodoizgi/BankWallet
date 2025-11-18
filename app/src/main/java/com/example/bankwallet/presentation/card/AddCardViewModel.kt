package com.example.bankwallet.presentation.card

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.TextRange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankwallet.domain.model.Card
import com.example.bankwallet.domain.model.CardFormValidationResult
import com.example.bankwallet.domain.usecase.AddCardUseCase
import com.example.bankwallet.domain.usecase.ValidateCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Add Card screen
 * Handles form state, validation and card creation
 */
@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val validateCardUseCase: ValidateCardUseCase,
    private val addCardUseCase: AddCardUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddCardScreenState())
    val state: StateFlow<AddCardScreenState> = _state.asStateFlow()

    fun onCardNameChange(value: String) {
        updateFormState { it.copy(cardName = value) }
    }

    fun onCardNumberChange(value: String) {
        val filtered = value.filter { it.isDigit() }.take(16)
        updateFormState { it.copy(cardNumber = filtered) }
    }

    fun onCvvChange(value: String) {
        val filtered = value.filter { it.isDigit() }.take(4)
        updateFormState { it.copy(cvv = filtered) }
    }

    fun onExpirationDateChange(value: TextFieldValue) {
        val digits = value.text.filter { it.isDigit() }
        val formattedValue = when {
            digits.length <= 2 -> digits
            else -> digits.take(2) + "/" + digits.drop(2)
        }.take(5)

        val newFieldValue = TextFieldValue(
            text = formattedValue,
            selection = TextRange(formattedValue.length)
        )

        updateFormState { it.copy(expirationDate = newFieldValue) }
    }

    fun onOwnerNameChange(value: String) {
        updateFormState { it.copy(ownerName = value) }
    }

    fun validateAllFields() {
        val currentForm = _state.value.formState
        val validationResult = validateCardUseCase(
            cardName = currentForm.cardName,
            cardNumber = currentForm.cardNumber,
            cvv = currentForm.cvv,
            expirationDate = currentForm.expirationDate.text,
            ownerName = currentForm.ownerName
        )

        updateErrorState(validationResult)
        updateFormValidState(validationResult)
    }

    fun submitForm() {
        validateAllFields()

        if (_state.value.isFormValid) {
            addCard()
        }
    }

    private fun addCard() {
        viewModelScope.launch {
            try {
                val formState = _state.value.formState
                val card = Card(
                    cardName = formState.cardName,
                    cardNumber = formState.cardNumber,
                    cvv = formState.cvv,
                    expirationDate = formState.expirationDate.text,
                    ownerName = formState.ownerName
                )

                _state.update { it.copy(isLoading = true) }
                addCardUseCase(card)
                _state.update { it.copy(isLoading = false) }

                resetForm()
            } catch (exception: Exception) {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun updateFormState(block: (CardFormState) -> CardFormState) {
        _state.update { currentState ->
            currentState.copy(formState = block(currentState.formState))
        }
    }

    private fun updateErrorState(result: CardFormValidationResult) {
        _state.update { currentState ->
            currentState.copy(errorState = CardFormErrorState.fromValidationResult(result))
        }
    }

    private fun updateFormValidState(result: CardFormValidationResult) {
        _state.update { currentState ->
            currentState.copy(isFormValid = result.isValid)
        }
    }

    private fun resetForm() {
        _state.value = AddCardScreenState()
    }
}

