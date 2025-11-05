package com.example.bankwallet.domain.usecase

import com.example.bankwallet.domain.repository.CardRepository
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val repository: CardRepository
) {
    suspend operator fun invoke() = repository.getAllCards()
}