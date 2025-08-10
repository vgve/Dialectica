package com.vicgcode.dialectica.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vicgcode.dialectica.R
import com.vicgcode.dialectica.data.models.DialectTheme

@Composable
fun DialectSectionItem(
    theme: DialectTheme,
    onClick: (DialectTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(120.dp)
            .padding(8.dp)
            .clickable { onClick(theme) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Circular image with conditional border
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (theme.isChosen == true) colorResource(R.color.black)
                    else colorResource(R.color.light_grey),
                    shape = CircleShape
                )
                .background(Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = theme.src),
                contentDescription = theme.name,
                modifier = Modifier.size(48.dp),
            )
        }

        // Title text
        Text(
            text = theme.name,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Preview with both states
@Preview
@Composable
fun ThemeItemPreview() {
    MaterialTheme {
        Column {
            DialectSectionItem(
                theme = DialectTheme(
                    id = "1",
                    name = "Relationships",
                    src = R.drawable.ic_handshake,
                    isChosen = true
                ),
                onClick = {}
            )
        }
    }
}
