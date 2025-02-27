package com.example.flashcards.designpatterns

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashcards.designpatterns.model.DesignPattern
import com.example.flashcards.designpatterns.model.DesignPatternsRepository
import com.example.flashcards.designpatterns.model.PatternType
import com.example.flashcards.designpatterns.ui.theme.DesignPatternFlashcardsTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DesignPatternList(
    designPatterns: List<DesignPattern>,
    modifier: Modifier = Modifier
) {
    val state = rememberLazyListState()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = state,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
        modifier = modifier
    ) {
        items(designPatterns) { designPattern ->
            DesignPatternListItem(designPattern = designPattern)
        }
    }
}

@Composable
fun DesignPatternListItem(
    designPattern: DesignPattern,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxHeight()
            .width(LocalConfiguration.current.screenWidthDp.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (designPattern.type) {
                PatternType.CREATIONAL -> MaterialTheme.colorScheme.primaryContainer
                PatternType.STRUCTURAL -> MaterialTheme.colorScheme.secondaryContainer
                PatternType.BEHAVIORAL -> MaterialTheme.colorScheme.tertiaryContainer
            },
            contentColor = when (designPattern.type) {
                PatternType.CREATIONAL -> MaterialTheme.colorScheme.primary
                PatternType.STRUCTURAL -> MaterialTheme.colorScheme.secondary
                PatternType.BEHAVIORAL -> MaterialTheme.colorScheme.tertiary
            }
        )
    ) {
        Column {
            Text(
                text = stringResource(id = designPattern.name),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(id = designPattern.description),
                style = MaterialTheme.typography.bodyMedium.copy(
                    hyphens = Hyphens.Auto,
                    lineBreak = LineBreak.Paragraph.copy(strategy = LineBreak.Strategy.HighQuality)
                ),
                overflow = TextOverflow.Ellipsis,
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            )
            Text(
                text = stringResource(id = designPattern.url),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DesignPatternListPreview() {
    DesignPatternFlashcardsTheme {
        DesignPatternList(DesignPatternsRepository.designPatterns)
    }
}

@Preview
@Composable
fun DesignPatternListItemPreview() {
    DesignPatternFlashcardsTheme {
        DesignPatternListItem(designPattern = DesignPatternsRepository.designPatterns[0])
    }
}