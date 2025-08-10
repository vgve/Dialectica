package com.vicgcode.dialectica.presentation.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vicgcode.dialectica.R

@Composable
fun SignUpScreen(
    navigateToHome: () -> Unit
) {

    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    // Handle navigation when sign up succeeds
    LaunchedEffect(key1 = uiState.isSignUpSuccessful) {
        if (uiState.isSignUpSuccessful == true) {
            navigateToHome()
        }
    }

    SignUpView(
        state = uiState,
        onEvent = { event ->
            viewModel.handleEvent(event)
        }
    )
}

@Composable
fun SignUpView(
    state: SignUpState = SignUpState(),
    onEvent: (SignUpEvent) -> Unit = {}
) {
    val name = rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = stringResource(R.string.app_name),
                color = colorResource(R.color.system_tertiary),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Welcome image
            Image(
                painter = painterResource(R.drawable.ill_welcome),
                contentDescription = null,
                modifier = Modifier
                    .height(250.dp)
                    .padding(top = 30.dp)
            )

            // Title
            Text(
                text = stringResource(R.string.signup_title),
                color = colorResource(R.color.system_secondary),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.lato_regular)),
                modifier = Modifier.padding(top = 30.dp)
            )

            // Subtitle
            Text(
                text = stringResource(R.string.signup_subtitle),
                color = colorResource(R.color.system_secondary),
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.lato_regular)),
                modifier = Modifier.padding(top = 8.dp)
            )

            // Name input
            OutlinedTextField(
                value = state.username ?: "",
                onValueChange = { onEvent(SignUpEvent.NameChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 20.dp),
                label = { Text(stringResource(R.string.common_name)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = FontFamily(Font(R.font.lato_regular))
                )
            )

            // Save button
            Button(
                onClick = { onEvent(SignUpEvent.OnSignUpClick) },
                enabled = !state.username.isNullOrEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 34.dp, vertical = 20.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(100.dp),
            ) {
                Text(
                    text = stringResource(R.string.common_save).uppercase(),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                    letterSpacing = 0.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpView()
}
