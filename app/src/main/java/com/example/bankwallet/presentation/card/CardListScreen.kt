package com.example.bankwallet.presentation.card

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bankwallet.R
import com.example.bankwallet.domain.model.Card
import com.example.bankwallet.ui.theme.LightGray
import com.example.bankwallet.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListScreen(
    viewModel: CardViewModel = hiltViewModel(),
    onAddClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("KARTLIK") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Card")
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(state.cards) { card ->
                CardItem(card = card, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CardItem(card: Card, viewModel: CardViewModel) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .height(240.dp)
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.card_background),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Column(Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {
                            Text(
                                text = "Kart Adı",
                                color = Color(LightGray.value),
                                fontSize = 12.sp
                            )

                            Text(
                                text = card.cardName,
                                Modifier.padding(bottom = 48.dp),
                                color = Color(White.value),
                                fontSize = 14.sp
                            )

                            Text(
                                text = "Kart Numarası",
                                color = Color(LightGray.value),
                                fontSize = 12.sp
                            )

                            Text(
                                text = "${card.cardNumber.take(4)}   ****   ****   ${
                                    card.cardNumber.takeLast(
                                        4
                                    )
                                }",
                                color = Color(White.value),
                                fontSize = 18.sp
                            )
                        }

                        Column {
                            Text(
                                text = "Ad Soyad",
                                color = Color(LightGray.value),
                                fontSize = 12.sp
                            )

                            Text(
                                text = card.ownerName,
                                color = Color(White.value),
                                fontSize = 14.sp
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(id = R.drawable.chip),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.TopEnd
                        )

                        IconButton(

                            onClick = { viewModel.handleIntent(CardIntent.DeleteCard(card)) }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete Card",
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Column {

                            Text(
                                text = "Ay / Yıl",
                                color = Color(LightGray.value),
                                fontSize = 12.sp
                            )

                            Text(
                                text = card.expirationDate,
                                color = Color(White.value),
                                fontSize = 14.sp
                            )

                            Text(
                                text = "Cvv",
                                modifier = Modifier.padding(top = 4.dp),
                                color = Color(LightGray.value),
                                fontSize = 12.sp
                            )

                            Text(text = card.cvv, color = Color(White.value), fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.card_background),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Button(
                    onClick = {
                        val clipboard =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("card_number", card.cardNumber)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Kart Numarası Kopyalandı!", Toast.LENGTH_SHORT)
                            .show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(0.dp),
                    colors = buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(White.value)
                    )
                ) {
                    Text("Kart Numarasını Kopyala", fontSize = 12.sp)
                }

                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    thickness = 1.dp,
                    color = Color(White.value)
                )

                Button(
                    onClick = {
                        val clipboard =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("owner_name", card.ownerName)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Ad Soyad  Kopyalandı!", Toast.LENGTH_SHORT)
                            .show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(0.dp),
                    colors = buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(White.value)
                    )
                ) {
                    Text("Ad Soyad Kopyala", fontSize = 12.sp)
                }
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color(White.value)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Button(
                    onClick = {
                        val clipboard =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("expiration_date", card.expirationDate)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Ay / Yıl Kopyalandı!", Toast.LENGTH_SHORT)
                            .show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(0.dp),
                    colors = buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(White.value)
                    )
                ) {
                    Text("Ay/Yıl Kopyala", fontSize = 12.sp)
                }

                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    thickness = 1.dp,
                    color = Color(White.value)
                )

                Button(
                    onClick = {
                        val clipboard =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("cvv", card.cvv)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Cvv Kopyalandı!", Toast.LENGTH_SHORT)
                            .show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(0.dp),
                    colors = buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(White.value)
                    )
                ) {
                    Text("Cvv Kopyala", fontSize = 12.sp)
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))
}