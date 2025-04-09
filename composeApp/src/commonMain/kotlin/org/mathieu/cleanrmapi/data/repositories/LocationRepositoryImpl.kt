package org.mathieu.cleanrmapi.data.repositories

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.data.local.LocationDAO
import org.mathieu.cleanrmapi.data.local.objects.LocationObject
import org.mathieu.cleanrmapi.data.local.objects.toDBObject
import org.mathieu.cleanrmapi.data.local.objects.toDetailedModel
import org.mathieu.cleanrmapi.data.remote.LocationApi
import org.mathieu.cleanrmapi.domain.character.CharacterRepository
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.domain.location.models.Location

internal class LocationRepositoryImpl(
    private val characterRepository: CharacterRepository
): LocationRepository {

    override suspend fun getLocation(id: Int): Location {

        val locationLocal = GetLocationObjectIfExists(locationId = id)

        return locationLocal.toDetailedModel(
            idsToCharactersConverter = characterRepository::getCharactersFromIdList
        )

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