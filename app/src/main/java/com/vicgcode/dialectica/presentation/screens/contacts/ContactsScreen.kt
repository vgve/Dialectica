package com.vicgcode.dialectica.presentation.screens.contacts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContactsScreen() {

    val viewModel: PersonalViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    ContactsView(
        state = uiState,
    )
}

@Composable
fun ContactsView(
    state: PersonalUiState = PersonalUiState(),
    // onEvent: (PersonalAction) -> Unit = {}
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
fun ContactsScreenPreview() {
    ContactsView()
}
