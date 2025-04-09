package org.mathieu.cleanrmapi.ui.screens.locationdetails

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
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
import org.mathieu.cleanrmapi.ui.core.composables.IconWithImage
import org.mathieu.cleanrmapi.ui.core.composables.Screen
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor
import org.mathieu.cleanrmapi.ui.core.composables.ErrorView
import org.mathieu.cleanrmapi.ui.core.composables.CharacterList
import org.mathieu.cleanrmapi.ui.core.composables.PreviewContent

/**
 * Displays the details for a given location.
 *
 * @param id The ID of the location to display.
 */
@Composable
fun LocationDetailsScreen(
    id: Int
) {

    Screen(
        viewModel = viewModel { LocationDetailsViewModel() }
    ) { state, viewModel ->

        LaunchedEffect(key1 = Unit) {
            viewModel.init(locationId = id)
        }

        Content(
            state = state,
            onClickBack = NavigationManager.navController::popBackStack,
            onAction = viewModel::handleAction
        )

    }

}

/**
 * Displays the location details screen based on the given state.
 *
 * Handles three states:
 * - Loading: Shows a loading indicator.
 * - Loaded: Shows location header and resident character grid.
 * - Error: Shows an error message.
 *
 * Includes a back button.
 *
 * @param state The current state of the location details.
 * @param onAction Callback for actions like selecting a character.
 * @param onClickBack Callback for the back button.
 */
@Composable
private fun Content(
    state: LocationDetailsState = LocationDetailsState.Loading,
    onAction: (LocationDetailsAction) -> Unit = { },
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
            is LocationDetailsState.Error -> ErrorView(error = it.message)
            is LocationDetailsState.Loaded -> Column {
                Header(state = it)
                // Characters' location display
                CharacterList(
                    characters = it.residents,
                    onCharacterClick = { character ->
                        onAction(LocationDetailsAction.SelectedCharacter(character))
                    }
                )
            }
            is LocationDetailsState.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}

/**
 * Displays the header for the location details screen.
 *
 * Shows the location's name, type, and dimension.
 *
 * @param state The `LocationDetailsState.Loaded` containing the location's details.
 * @throws IllegalArgumentException if `state` is not `LocationDetailsState.Loaded`.
 */
@Composable
private fun Header(state: LocationDetailsState.Loaded) {

    Column(
        modifier = Modifier
            .background(SurfaceColor)
            .fillMaxWidth()
            .padding(start = 48.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        IconWithImage(
            imageVector = Icons.Rounded.Home,
            orientation = Orientation.Horizontal,
            text = "${state.name} - ${state.type}"
        )

        Text(
            modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
            text = "Dimension - ${state.dimension}",
            maxLines = 1,
            fontSize = 11.sp
        )

    }

}

@Preview
@Composable
private fun LocationDetailsPreview() = PreviewContent {
    Content()
}
