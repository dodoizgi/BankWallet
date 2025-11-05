package com.example.bankwallet.presentation.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bankwallet.domain.model.Card

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    viewModel: CardViewModel = hiltViewModel(),
    onCardAdded: () -> Unit
) {
    var cardName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var bankName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Card") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = cardName,
                onValueChange = { cardName = it },
                label = { Text("Card Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cardNumber,
                onValueChange = { cardNumber = it },
                label = { Text("Card Number") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text("cvv") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = expirationDate,
                onValueChange = { expirationDate = it },
                label = { Text("Expiration Date") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = bankName,
                onValueChange = { bankName = it },
                label = { Text("Bank Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (cardName.isNotBlank() && cardNumber.isNotBlank()) {
                        viewModel.handleIntent(
                            CardIntent.AddCard(
                                Card(
                                    cardName = cardName,
                                    cardNumber = cardNumber,
                                    bankName = bankName,
                                    cvv = cvv,
                                    expirationDate = expirationDate
                                )
                            )
                        )
                        onCardAdded()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Card")
            }
        }
    }
}
