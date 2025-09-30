package com.vicgcode.dialectica.presentation.screens.favourite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FavouriteScreen() {

    val viewModel: FavouriteViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleEvent(FavouriteEvent.Initialized)
    }

    FavouriteView(
        state = uiState,
        onEvent = viewModel::handleEvent
    )
}

@Composable
fun FavouriteView(
    state: FavouriteState = FavouriteState(),
    onEvent: (FavouriteEvent) -> Unit = {}
) {
    val showDeleteDialog = rememberSaveable { mutableStateOf(false) }

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
fun FavouriteScreenPreview() {
    FavouriteView()
}
