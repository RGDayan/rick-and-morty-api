package org.mathieu.cleanrmapi.ui.screens.locationdetails

import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.location.usecases.GetLocationWithCharacters
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel

/**
 * Sealed interface representing actions that can be performed within the Location Details screen.
 * These actions typically trigger state updates or side effects in the view model.
 */
sealed interface LocationDetailsAction {
    data class SelectedCharacter(val character: Character): LocationDetailsAction
}

/**
 * ViewModel responsible for managing the state and actions related to the location details screen.
 *
 * This class fetches location details and their associated characters, and handles user actions
 * such as selecting a character to view their details.
 *
 * It extends [ViewModel] and utilizes [LocationDetailsState] for state management.
 * Its initial state is [LocationDetailsState.Loading].
 */
class LocationDetailsViewModel :
    ViewModel<LocationDetailsState>(LocationDetailsState.Loading) {
    fun init(locationId: Int) {

        fetchData(
            source = { GetLocationWithCharacters(locationId = locationId) }
        ) {
            onSuccess { details ->
                updateState {
                    LocationDetailsState.Loaded(
                        name = details.name,
                        type = details.type,
                        dimension = details.dimension,
                        residents = details.residents
                    )
                }
            }

            onFailure {
                updateState {
                    LocationDetailsState.Error(message = it.message ?: it.toString())
                }
            }

        }

    }


    fun handleAction(action: LocationDetailsAction) {
        when(action) {
            is LocationDetailsAction.SelectedCharacter -> selectedCharacter(action.character)
        }
    }


    private fun selectedCharacter(character: Character) =
        sendEvent(Destination.CharacterDetails(character.id.toString()))

}

/**
 * Represents the different states of loading and displaying location details.
 *
 * This sealed interface defines the possible states for a location details view or component.
 * It ensures that all possible states are handled when working with location details.
 */
sealed interface LocationDetailsState {
    data object Loading : LocationDetailsState

    data class Error(val message: String) : LocationDetailsState

    data class Loaded(
        val name: String,
        val type: String,
        val dimension: String,
        val residents: List<Character>
    ) : LocationDetailsState

}