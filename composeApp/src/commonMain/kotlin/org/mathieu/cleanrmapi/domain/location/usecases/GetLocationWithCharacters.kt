package org.mathieu.cleanrmapi.domain.location.usecases

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.domain.location.models.LocationWithCharacters

/**
 * `GetLocationWithCharacters` is responsible for fetching a `Location`
 * along with its associated `Character` residents.
 *
 * It leverages the `LocationRepository` to retrieve both the location details and the list of characters
 * that reside within that location.
 *
 * This object acts as an interactor or use case in a clean architecture, encapsulating the logic
 * required to retrieve a `Location` and its `Character` residents.
 */
object GetLocationWithCharacters : KoinComponent{

    private val locationRepository: LocationRepository by inject()

    suspend operator fun invoke(locationId: Int): LocationWithCharacters {

        val location = locationRepository.getLocation(locationId)

        val residents = locationRepository.getCharactersFrom(locationId)

        return LocationWithCharacters(
            location = location,
            residents = residents
        )
    }
}