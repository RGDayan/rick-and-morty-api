package org.mathieu.cleanrmapi.ui.screens.characterdetails

import org.koin.core.component.inject
import org.mathieu.cleanrmapi.common.SoundPlayer
import org.mathieu.cleanrmapi.domain.character.CharacterRepository
import org.mathieu.cleanrmapi.domain.character.models.CharacterGender
import org.mathieu.cleanrmapi.domain.character.models.CharacterStatus
import org.mathieu.cleanrmapi.domain.episode.models.Episode
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel

sealed interface CharacterDetailsAction {
    data class SelectedEpisode(val episode: Episode): CharacterDetailsAction
    data class LocationSelection(val location: LocationPreview): CharacterDetailsAction
}

class CharacterDetailsViewModel :
    ViewModel<CharacterDetailsState>(CharacterDetailsState.Loading) {

    private val characterRepository: CharacterRepository by inject()
    private val soundPlayer: SoundPlayer by inject()

    fun init(characterId: Int) {

        fetchData(
            source = { characterRepository.getCharacterDetailed(id = characterId) }
        ) {

            onSuccess { details ->
                updateState {
                    CharacterDetailsState.Loaded(
                        name = details.name,
                        avatarUrl = details.avatarUrl,
                        episodes = details.episodes,
                        status = details.status,
                        gender = details.gender,
                        origin = details.origin,
                        location = details.location
                    )
                }
            }

            onFailure {
                updateState {
                    CharacterDetailsState.Error(message = it.message ?: it.toString())
                }
            }
        }
    }

    /**
     * Handles actions triggered within the Character Details screen.
     *
     * This function processes different actions related to the character details,
     * such as selecting an episode or a location. Based on the action type,
     * it performs specific operations like navigating to other screens or
     * triggering side effects.
     *
     * @param action The [CharacterDetailsAction] to be handled. This represents
     *               an event or interaction that occurred within the Character
     *               Details screen.
     *
     * @see CharacterDetailsAction
     * @see Destination
     * @see soundPlayer
     * @see sendEvent
     */
    fun handleAction(action: CharacterDetailsAction) {
        when(action) {
            is CharacterDetailsAction.SelectedEpisode ->
                sendEvent(Destination.EpisodeDetails(action.episode.id.toString()))

            is CharacterDetailsAction.LocationSelection -> {
                soundPlayer.playSound()
                sendEvent(Destination.LocationDetails(action.location.id.toString()))
            }
        }
    }


}

sealed interface CharacterDetailsState {
    object Loading : CharacterDetailsState

    data class Error(val message: String) : CharacterDetailsState

    data class Loaded(
        val name: String,
        val avatarUrl: String,
        val episodes: List<Episode>,
        val status: CharacterStatus,
        val gender: CharacterGender,
        val origin: LocationPreview,
        val location: LocationPreview,
    ) : CharacterDetailsState

}