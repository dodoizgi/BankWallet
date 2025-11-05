package com.example.bankwallet.presentation.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankwallet.domain.model.Card
import com.example.bankwallet.domain.usecase.AddCardUseCase
import com.example.bankwallet.domain.usecase.DeleteCardUseCase
import com.example.bankwallet.domain.usecase.GetCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val addCardUseCase: AddCardUseCase,
    private val deleteCardUseCase: DeleteCardUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CardState())
    val state: StateFlow<CardState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CardEffect>()
    val effect: SharedFlow<CardEffect> = _effect.asSharedFlow()

    init {
        handleIntent(CardIntent.LoadCards)
    }

    fun handleIntent(intent: CardIntent) {
        when (intent) {
            is CardIntent.LoadCards -> loadCards()
            is CardIntent.AddCard -> addCard(intent.card)
            is CardIntent.DeleteCard -> deleteCard(intent.card)
        }
    }

    private fun loadCards() {
        viewModelScope.launch {
            getCardsUseCase().collect { cards ->
                _state.update { it.copy(cards = cards, isLoading = false) }
            }
        }
    }

    private fun addCard(card: Card) {
        viewModelScope.launch {
            addCardUseCase(card)
            _effect.emit(CardEffect.ShowToast("Card added successfully"))
        }
    }

    private fun deleteCard(card: Card) {
        viewModelScope.launch {
            deleteCardUseCase(card)
            _effect.emit(CardEffect.ShowToast("Card deleted successfully"))
        }
    }
}