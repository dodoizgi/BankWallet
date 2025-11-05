package com.example.bankwallet.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bankwallet.presentation.card.CardListScreen

@Composable
fun CardVaultAppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "cardList"
    ) {
        composable("cardList") {
            CardListScreen()
        }
    }
}