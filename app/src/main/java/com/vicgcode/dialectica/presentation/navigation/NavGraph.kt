package com.vicgcode.dialectica.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vicgcode.dialectica.presentation.screens.contacts.ContactsScreen
import com.vicgcode.dialectica.presentation.screens.conversation.ConversationScreen
import com.vicgcode.dialectica.presentation.screens.favourite.FavouriteScreen
import com.vicgcode.dialectica.presentation.screens.home.HomeScreen
import com.vicgcode.dialectica.presentation.screens.signup.SignUpScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.SignUp.route
    ) {
        composable(NavRoute.SignUp.route) {
            SignUpScreen()
        }

        composable(NavRoute.Home.route) {
            HomeScreen()
        }

        composable(NavRoute.Favourite.route) {
            FavouriteScreen()
        }

        composable(NavRoute.Contacts.route) {
            ContactsScreen()
        }

        composable(NavRoute.Conversation.route) {
            ConversationScreen()
        }
    }
}
