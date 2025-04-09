package org.mathieu.cleanrmapi.data.repositories

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.common.toList
import org.mathieu.cleanrmapi.data.local.LocationDAO
import org.mathieu.cleanrmapi.data.local.objects.LocationObject
import org.mathieu.cleanrmapi.data.local.objects.toDBObject
import org.mathieu.cleanrmapi.data.local.objects.toDetailedModel
import org.mathieu.cleanrmapi.data.local.objects.toModel
import org.mathieu.cleanrmapi.data.remote.CharacterApi
import org.mathieu.cleanrmapi.data.remote.LocationApi
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.domain.location.models.Location

internal class LocationRepositoryImpl(
    private val characterApi: CharacterApi
): LocationRepository {

    override suspend fun getLocation(id: Int): Location {

        val locationLocal = GetLocationObjectIfExists(locationId = id)

        return locationLocal.toDetailedModel(
            idsToCharactersConverter = ::getCharactersFromIdList
        )

    }


    /**
     * Retrieves a list of characters by their IDs.
     *
     * Fetches character data from the API using a comma-separated list of IDs.
     * Handles both single and multiple ID requests.
     *
     * @param idList A comma-separated string of character IDs (e.g., "1,2,3" or "5").
     * @return A list of [Character] objects. Returns an empty list if no characters are found or if an error occurs.
     * @throws NumberFormatException If a single ID cannot be parsed as an integer.
     * @throws Exception If a network error occurs.
     * @see Character
     * @see characterApi
     * @see MustBeCommaSeparatedIds
     */
    private suspend fun getCharactersFromIdList(@MustBeCommaSeparatedIds idList: String): List<Character> {

        //TODO: fetch system

        return if (idList.contains(",")) {
            val charactersResponse = characterApi.getCharactersFromIds(ids = idList)
            charactersResponse.map { it.toDBObject().toModel() }
        } else {
            val characterResponse = characterApi.getCharacter(idList.toInt())
            characterResponse?.toDBObject()?.toModel()?.toList() ?: emptyList()
        }

    }

}


/**
 * `GetLocationObjectIfExists` retrieves a `LocationObject` by ID, prioritizing the local database.
 *
 * It first checks the local database. If not found, it fetches from the remote API, saves it locally, and returns it.
 * If not found in either source, it throws an exception.
 */
private object GetLocationObjectIfExists : KoinComponent {

    private val locationApi: LocationApi by inject()
    private val locationLocal: LocationDAO by inject()


    suspend operator fun invoke(locationId: Int): LocationObject =
        tryToGetLocationLocally(locationId)
            .fetchRemotelyIfNotFound(locationId)
            .throwIfWeCannotFindIt()


    private suspend fun tryToGetLocationLocally(id: Int) = locationLocal.getLocation(id)

    private suspend fun LocationObject?.fetchRemotelyIfNotFound(id: Int): LocationObject? {
        if (this != null) return this

        return locationApi.getLocation(id = id)
            ?.toDBObject()
            ?.also { obj ->
                locationLocal.insert(obj)
            }
    }

    private fun LocationObject?.throwIfWeCannotFindIt(): LocationObject {
        if (this != null) return this
        throw Exception("Could not find Location locally and remotely.")
    }

}