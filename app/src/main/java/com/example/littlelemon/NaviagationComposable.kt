package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationComposable(viewModel: List<MenuItemEntity>, searchPhrase: String, onSearchPhraseChange: (String) -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destinations.Onboarding) {
        composable(Destinations.Onboarding) {
            Onboarding(navController)
        }
        composable(Destinations.Home) {
            Home(navController, viewModel, searchPhrase, onSearchPhraseChange)
        }
        composable(Destinations.Profile) {
            Profile(navController)
        }
    }
}