package com.vicgcode.dialectica.presentation.screens.favourite

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
import com.vicgcode.dialectica.presentation.favourite.FavouriteAction
import com.vicgcode.dialectica.presentation.favourite.FavouriteUiState
import com.vicgcode.dialectica.presentation.favourite.FavouriteViewModel

@Composable
fun FavouriteScreen() {

    val viewModel: FavouriteViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    FavouriteView(
        state = uiState,
    )
}

@Composable
fun FavouriteView(
    state: FavouriteUiState = FavouriteUiState(),
    onEvent: (FavouriteAction) -> Unit = {}
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
fun FavouriteScreenPreview() {
    FavouriteView()
}
