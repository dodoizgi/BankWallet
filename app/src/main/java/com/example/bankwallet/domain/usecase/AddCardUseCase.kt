package com.example.bankwallet.domain.usecase

import com.example.bankwallet.domain.repository.CardRepository
import com.example.bankwallet.domain.model.Card
import javax.inject.Inject

class AddCardUseCase @Inject constructor(
    private val repository: CardRepository
) {
    suspend operator fun invoke(card: Card) = repository.addCard(card)
}