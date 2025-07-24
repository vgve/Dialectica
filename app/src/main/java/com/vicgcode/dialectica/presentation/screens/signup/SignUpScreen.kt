package com.vicgcode.dialectica.presentation.screens.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vicgcode.dialectica.presentation.signup.SignUpAction
import com.vicgcode.dialectica.presentation.signup.SignUpUiState
import com.vicgcode.dialectica.presentation.signup.SignUpViewModel

@Composable
fun SignUpScreen() {

    val viewModel: SignUpViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    SignUpView(
        state = uiState,
    )
}

@Composable
fun SignUpView(
    state: SignUpUiState = SignUpUiState(),
    onEvent: (SignUpAction) -> Unit = {}
) {

}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpView()
}
