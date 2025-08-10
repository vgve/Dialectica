package com.vicgcode.dialectica.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vicgcode.dialectica.R
import com.vicgcode.dialectica.presentation.components.DialectSectionItem
import com.vicgcode.dialectica.presentation.components.QuestionCard

@Composable
fun HomeScreen() {

    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    HomeView(
        state = uiState,
        onEvent = { event -> viewModel.handleEvent(event) }
    )
}

@Composable
fun HomeView(
    state: HomeState = HomeState(),
    onEvent: (HomeEvent) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(HomeEvent.OnRandomClick) },
                elevation = FloatingActionButtonDefaults.elevation(6.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_magic),
                    contentDescription = stringResource(R.string.random),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Themes section
            ThemesHeader(
                title = stringResource(R.string.title_themes),
                modifier = Modifier.fillMaxWidth()
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                items(state.sections) { section ->
                    DialectSectionItem(
                        theme = section,
                        onClick = { onEvent(HomeEvent.OnThemeSelected(section)) }
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                thickness = 1.dp,
                color = Color.Gray
            )
            // Question section
            state.currentQuestion?.text?.let {
                QuestionCard(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
            // Actions row
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 14.dp)
            ) {
                IconButton(
                    onClick = { onEvent(HomeEvent.OnAddToContactsClick) },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_person_menu),
                        contentDescription = stringResource(R.string.add_personal),
                    )
                }

                IconButton(
                    onClick = { onEvent(HomeEvent.OnAddFavoriteClick) },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_fav_menu),
                        contentDescription = stringResource(R.string.add_favorite),
                    )
                }
            }

            // Next button
            Button(
                onClick = { onEvent(HomeEvent.OnNextClick) },
                modifier = Modifier
                    .width(200.dp)
                    .padding(30.dp)
                    .align(Alignment.CenterHorizontally),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow),
                        contentDescription = stringResource(R.string.next),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.next))
                }
            }
        }
    }
}

@Composable
private fun ThemesHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        HorizontalDivider(
            modifier = Modifier
                .width(100.dp)
                .weight(1f),
            thickness = 1.dp,
            color = Color.Gray
        )
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .width(100.dp)
                .weight(1f),
            thickness = 1.dp,
            color = Color.Gray
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeView()
}
