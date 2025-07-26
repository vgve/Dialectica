package com.vicgcode.dialectica.presentation.navigation

sealed class NavRoute(val route : String) {

    data object Splash : NavRoute("splash")
    data object SignUp : NavRoute("signup")
    data object Home : NavRoute("home")
    data object Favourite : NavRoute("favourite")
    data object Contacts: NavRoute("contacts")
    data object Conversation: NavRoute("conversation")
}
