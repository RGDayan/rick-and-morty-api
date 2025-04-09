package org.mathieu.cleanrmapi.ui.screens.episodedetails

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.mathieu.cleanrmapi.ui.NavigationManager
import org.mathieu.cleanrmapi.ui.core.composables.BackArrow
import org.mathieu.cleanrmapi.ui.core.composables.PreviewContent
import org.mathieu.cleanrmapi.ui.core.composables.Screen
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor
import org.mathieu.cleanrmapi.ui.core.composables.ErrorView
import org.mathieu.cleanrmapi.ui.core.composables.CharacterList

@Composable
fun EpisodeDetailsScreen(
    id: Int
) {

    Screen(
        viewModel = viewModel { EpisodeDetailsViewModel() },
    ) { state, viewModel ->

        LaunchedEffect(key1 = Unit) {
            viewModel.init(episodeId = id)
        }

        Content(
            state = state,
            onClickBack = NavigationManager.navController::popBackStack,
            onAction = viewModel::handleAction
        )

    }

}

@Composable
private fun Content(
    state: EpisodeDetailsState = EpisodeDetailsState.Loading,
    onAction: (EpisodeDetailsAction) -> Unit = { },
    onClickBack: () -> Unit = { }
) = Box(
    modifier = Modifier
        .fillMaxSize()
        .padding(),
    contentAlignment = Alignment.Center
) {

    BackArrow(
        modifier = Modifier
            .align(Alignment.TopStart)
            .zIndex(1f),
        onClick = onClickBack
    )

    Crossfade(targetState = state) {
        when (it) {
            is EpisodeDetailsState.Error -> ErrorView(error = it.message)
            is EpisodeDetailsState.Loaded -> Column {
                Header(state = it)
                CharacterList(
                    characters = it.characters,
                    onCharacterClick = { character ->
                        onAction(EpisodeDetailsAction.SelectedCharacter(character))
                    }
                )
            }
            EpisodeDetailsState.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}

/**
 * Displays the header for an episode.
 *
 * Shows the episode's air date and title. The title uses a marquee effect for long text.
 *
 * Previously encapsulated in a Kotlin object.
 * Now a separated composable function from the body of the EpisodeDetailsScreen for clarity and reusability of CharacterList.
 *
 * @param state The loaded episode data containing the air date, episode number, and name.
 */
@Composable
private fun Header(state: EpisodeDetailsState.Loaded) {

    Column(
        modifier = Modifier
            .background(SurfaceColor)
            .fillMaxWidth()
            .padding(start = 48.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        Text(text = state.airDate, fontSize = 11.sp)

        Text(
            modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
            text = "${state.episode} - ${state.name}",
            maxLines = 1
        )

    }

}

@Preview
@Composable
private fun CharacterDetailsPreview() = PreviewContent {
    Content()
}

