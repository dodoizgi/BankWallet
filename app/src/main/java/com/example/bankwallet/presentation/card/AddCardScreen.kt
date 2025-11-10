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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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

    var cardNameError by remember { mutableStateOf<String?>(null) }
    var cardNumberError by remember { mutableStateOf<String?>(null) }
    var cvvError by remember { mutableStateOf<String?>(null) }
    var expirationDateError by remember { mutableStateOf<String?>(null) }

    fun validateCardName(name: String): String? {
        return when {
            name.isBlank() -> "Kart adı boş olamaz"
            name.length > 30 -> "Kart adı 30 karakterden uzun olamaz"
            else -> null
        }
    }

    fun validateCardNumber(number: String): String? {
        return when {
            number.isBlank() -> "Kart numarası boş olamaz"
            !number.all { it.isDigit() } -> "Sadece rakam giriniz"
            number.length != 16 -> "Kart numarası 16 haneli olmalıdır"
            else -> null
        }
    }

    fun validateCvv(cvv: String): String? {
        return when {
            cvv.isBlank() -> "CVV boş olamaz"
            !cvv.all { it.isDigit() } -> "Sadece rakam giriniz"
            cvv.length !in 3..4 -> "CVV 3 veya 4 haneli olmalıdır"
            else -> null
        }
    }

    fun validateExpirationDate(date: String): String? {
        return when {
            date.isBlank() -> "Son kullanma tarihi boş olamaz"
            !date.matches(Regex("^(0[1-9]|1[0-2])/([0-9]{2})\$")) -> "Geçerli bir tarih giriniz (AA/YY)"
            else -> null
        }
    }

    fun isFormValid(): Boolean {
        val nameValid = validateCardName(cardName) == null
        val numberValid = validateCardNumber(cardNumber) == null
        val cvvValid = validateCvv(cvv) == null
        val dateValid = validateExpirationDate(expirationDate) == null
        return nameValid && numberValid && cvvValid && dateValid
    }

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
                onValueChange = {
                    cardName = it
                    cardNameError = validateCardName(it)
                },
                label = { Text("Kart Adı") },
                modifier = Modifier.fillMaxWidth(),
                isError = cardNameError != null,
                supportingText = {
                    cardNameError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = cardNumber,
                onValueChange = {
                    if (it.length <= 16 && (it.isEmpty() || it.all { c -> c.isDigit() })) {
                        cardNumber = it
                        cardNumberError = validateCardNumber(it)
                    }
                },
                label = { Text("Kart Numarası") },
                modifier = Modifier.fillMaxWidth(),
                isError = cardNumberError != null,
                supportingText = {
                    cardNumberError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = cvv,
                onValueChange = {
                    if (it.length <= 4 && (it.isEmpty() || it.all { c -> c.isDigit() })) {
                        cvv = it
                        cvvError = validateCvv(it)
                    }
                },
                label = { Text("CVV") },
                modifier = Modifier.fillMaxWidth(),
                isError = cvvError != null,
                supportingText = {
                    cvvError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = expirationDate,
                onValueChange = {
                    if (it.length <= 5) {
                        val formattedValue = if (it.length == 2 && expirationDate.length == 1) {
                            "$it/"
                        } else {
                            it
                        }
                        expirationDate = formattedValue
                        expirationDateError = validateExpirationDate(formattedValue)
                    }
                },
                label = { Text("Son Kullanma Tarihi (AA/YY)") },
                modifier = Modifier.fillMaxWidth(),
                isError = expirationDateError != null,
                supportingText = {
                    expirationDateError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    cardNameError = validateCardName(cardName)
                    cardNumberError = validateCardNumber(cardNumber)
                    cvvError = validateCvv(cvv)
                    expirationDateError = validateExpirationDate(expirationDate)

                    if (isFormValid()) {
                        viewModel.handleIntent(
                            CardIntent.AddCard(
                                Card(
                                    cardName = cardName,
                                    cardNumber = cardNumber,
                                    cvv = cvv,
                                    expirationDate = expirationDate
                                )
                            )
                        )
                        onCardAdded()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("Kartı Kaydet")
            }
        }
    }
}
