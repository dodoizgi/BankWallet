package com.example.bankwallet.presentation.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Add Card Screen Composable
 * Handles UI rendering only, delegates state management to ViewModel
 *
 * SOLID Principles Applied:
 * - SRP: Screen only renders UI, ViewModel handles logic
 * - DIP: Dependencies injected via Hilt
 * - OCP: Can add new validation rules without modifying UI
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    viewModel: AddCardViewModel = hiltViewModel(),
    onCardAdded: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isFormValid) {
        if (state.isFormValid) {
            onCardAdded()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Yeni Kart Ekle") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // Card Name Field
            CardFormTextField(
                value = state.formState.cardName,
                onValueChange = viewModel::onCardNameChange,
                label = "Kart Adı",
                error = state.errorState.cardNameError,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Card Number Field
            CardFormTextField(
                value = state.formState.cardNumber,
                onValueChange = viewModel::onCardNumberChange,
                label = "Kart Numarası",
                error = state.errorState.cardNumberError,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(8.dp))

            // CVV Field
            CardFormTextField(
                value = state.formState.cvv,
                onValueChange = viewModel::onCvvChange,
                label = "CVV",
                error = state.errorState.cvvError,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Expiration Date Field
            OutlinedTextField(
                value = state.formState.expirationDate,
                onValueChange = viewModel::onExpirationDateChange,
                label = { Text("Son Kullanma Tarihi (AA/YY)") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.errorState.expirationDateError != null,
                supportingText = {
                    state.errorState.expirationDateError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Owner Name Field
            CardFormTextField(
                value = state.formState.ownerName,
                onValueChange = viewModel::onOwnerNameChange,
                label = "Kart Sahibi",
                error = state.errorState.ownerNameError,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = viewModel::submitForm,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                Text(if (state.isLoading) "Kaydediliyor..." else "Kartı Kaydet")
            }
        }
    }
}

/**
 * Reusable text field component for card form
 */
@Composable
private fun CardFormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String?,
    keyboardType: KeyboardType,
    imeAction: ImeAction
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        isError = error != null,
        supportingText = {
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        )
    )
}
