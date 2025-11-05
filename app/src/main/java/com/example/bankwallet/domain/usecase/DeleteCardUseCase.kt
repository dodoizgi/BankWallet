package com.example.bankwallet.domain.usecase

import com.example.bankwallet.domain.CardRepository
import com.example.bankwallet.domain.model.Card
import javax.inject.Inject

class DeleteCardUseCase @Inject constructor(
    private val repository: CardRepository
) {
    suspend operator fun invoke(card: Card) = repository.deleteCard(card)
}