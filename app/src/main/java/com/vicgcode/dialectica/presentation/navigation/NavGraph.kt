package com.vicgcode.dialectica.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.vicgcode.dialectica.presentation.screens.contacts.ContactsScreen
import com.vicgcode.dialectica.presentation.screens.conversation.ConversationScreen
import com.vicgcode.dialectica.presentation.screens.favourite.FavouriteScreen
import com.vicgcode.dialectica.presentation.screens.home.HomeScreen
import com.vicgcode.dialectica.presentation.screens.signup.SignUpScreen
import com.vicgcode.dialectica.presentation.screens.splash.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController  = navController,
        startDestination = NavRoute.Splash.route
    ){
        composable(route = NavRoute.Splash.route){
            SplashScreen(
                navigateToHome = {
                    navController.navigate(NavRoute.Home.route)
                },
                navigateToSignUp = {
                    navController.navigate(NavRoute.SignUp.route)
                }
            )
        }
        startGraph(navController)
        mainGraph(navController)
    }
}

fun NavGraphBuilder.startGraph(
    navController: NavHostController,
) {
    navigation(
        route = NavRoute.SignUp.route,
        startDestination = NavRoute.SignUp.route
    ) {
        composable(NavRoute.SignUp.route) {
            SignUpScreen(
                navigateToHome = {
                    navController.navigate(NavRoute.Home.route)
                }
            )
        }
    }
}

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
) {
    navigation(
        route = NavRoute.Home.route,
        startDestination = NavRoute.Home.route
    ) {
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
