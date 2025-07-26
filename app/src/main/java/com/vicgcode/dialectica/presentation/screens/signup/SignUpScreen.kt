package com.vicgcode.dialectica.presentation.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vicgcode.dialectica.R
import com.vicgcode.dialectica.presentation.signup.SignUpAction
import com.vicgcode.dialectica.presentation.signup.SignUpUiState
import com.vicgcode.dialectica.presentation.signup.SignUpViewModel

@Composable
fun SignUpScreen(
    navigateToHome: () -> Unit
) {

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
    val name = rememberSaveable { mutableStateOf("") }

    Scaffold(modifier = Modifier
        .padding(16.dp),
        floatingActionButton = { },
        content = { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(30.dp))
                Image(
                    painter = painterResource(R.drawable.ill_welcome),
                    contentDescription = "",
                )
                Spacer(Modifier.height(30.dp))
                Text(
                    text = stringResource(R.string.signup_title),
                    fontSize = 20.sp,
                )
                Spacer(Modifier.height(20.dp))
                TextField(
                    value = name.value,
                    onValueChange = {
                        name.value = it
                    }
                )
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        onEvent(SignUpAction.OnAuthSuccess)
                    },
                ) {
                    Text(
                        text = stringResource(R.string.common_save)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpView()
}
