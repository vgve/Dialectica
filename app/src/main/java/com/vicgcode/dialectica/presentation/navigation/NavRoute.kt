package com.vicgcode.dialectica.presentation.navigation

sealed class NavRoute(val route : String) {

    // Flows
    data object StartFlow : NavRoute("start_flow")
    data object MainFlow : NavRoute("main_flow")

    // Screens
    data object Splash : NavRoute("splash")
    data object SignUp : NavRoute("signup")
    data object Home : NavRoute("home")
    data object Favourite : NavRoute("favourite")
    data object Contacts: NavRoute("contacts")
    data object Conversation: NavRoute("conversation")
}
