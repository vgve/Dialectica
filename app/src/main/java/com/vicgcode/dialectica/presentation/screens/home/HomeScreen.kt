package com.vicgcode.dialectica.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vicgcode.dialectica.presentation.home.HomeAction
import com.vicgcode.dialectica.presentation.home.HomeUiState
import com.vicgcode.dialectica.presentation.home.HomeViewModel

@Composable
fun HomeScreen() {

    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    HomeView(
        state = uiState,
    )
}

@Composable
fun HomeView(
    state: HomeUiState = HomeUiState(),
    onEvent: (HomeAction) -> Unit = {}
) {
    Scaffold(modifier = Modifier
        .padding(16.dp),
        floatingActionButton = { },
        content = { innerPadding ->
            Box(Modifier.padding(innerPadding))

        }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeView()
}
