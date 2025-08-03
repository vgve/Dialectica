package com.vicgcode.dialectica.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.vicgcode.dialectica.R
import com.vicgcode.dialectica.presentation.MainViewModel
import com.vicgcode.dialectica.presentation.SplashState

private const val DELAY: Long = 2000

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
) {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        when (state) {
            SplashState.Loading -> { }
            SplashState.Login -> navigateToSignUp.invoke()
            SplashState.Home -> navigateToHome.invoke()
            is SplashState.Error -> { }
        }
    }

    SplashView()
}

@Composable
fun SplashView() {

    Surface() {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.ic_dialectica_logo),
                contentDescription = "",
            )
        }
    }
}
