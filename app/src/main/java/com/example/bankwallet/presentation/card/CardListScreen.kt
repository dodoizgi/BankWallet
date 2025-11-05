package com.example.bankwallet.presentation.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankwallet.domain.model.Card

@Composable
fun CardListScreen(viewModel: CardViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()
    val effect = viewModel.effect

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // TODO: navigate to AddCardScreen
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Card")
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(state.value.cards) { card ->
                CardItem(card = card, onCopy = {
                    // TODO: implement copy
                })
            }
        }
    }
}

@Composable
fun CardItem(card: Card, onCopy: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = card.bankName, style = MaterialTheme.typography.titleMedium)
            Text(text = card.cardName)
            Text(text = "**** **** **** ${card.cardNumber.takeLast(4)}")
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onCopy) {
                Text("Copy Number")
            }
        }
    }
}