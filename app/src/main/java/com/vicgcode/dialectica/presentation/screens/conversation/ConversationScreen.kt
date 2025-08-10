package com.vicgcode.dialectica.presentation.screens.conversation

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
fun ConversationScreen() {

    val viewModel: TalkViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    ConversationView(
        state = uiState,
    )
}

@Composable
fun ConversationView(
    state: TalkUiState = TalkUiState(),
    onEvent: (TalkAction) -> Unit = {}
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
fun ConversationScreenPreview() {
    ConversationView()
}
