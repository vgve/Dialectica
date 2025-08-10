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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.vicgcode.dialectica.R

private const val DELAY: Long = 2000

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
) {
    val viewModel: SplashViewModel = hiltViewModel()
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

@Preview
@Composable
fun SplashViewPreview() {
    SplashView()
}
