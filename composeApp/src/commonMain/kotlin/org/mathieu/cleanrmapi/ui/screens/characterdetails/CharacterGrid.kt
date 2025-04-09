package org.mathieu.cleanrmapi.ui.screens.characterdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.ui.core.composables.CharacterCard


/**
 * A composable function that displays a grid of character cards.
 *
 * @param characters The list of [Character] objects to display.
 * @param onCharacterClick A lambda function that is invoked when a character card is clicked.
 *                         It receives the clicked [Character] as a parameter.
 */
@Composable
fun CharacterGrid(
    characters : List<Character>,
    onCharacterClick : (Character) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(characters) { character ->

                CharacterCard(
                    modifier = Modifier
                        .clickable {
                            onCharacterClick(character)
                        },
                    character = character
                )
            }
        }
    }
}