package org.mathieu.cleanrmapi.domain.location

import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.location.models.Location

/**
 * Interface for retrieving location data.
 *
 * This interface defines the contract for a repository that can fetch location information
 * based on a given ID. Implementations of this interface are responsible for
 * retrieving the location data from a specific source (e.g., database, network).
 */
interface LocationRepository {

    /**
     * Retrieves a location by its ID.
     *
     * @param id The ID of the location to retrieve. Must be positive.
     * @return The [Location] object.
     * @throws IllegalArgumentException if the ID is not positive.
     * @throws NoSuchElementException if no location is found with the given ID.
     */
    suspend fun getLocation(id: Int): Location


    /**
     * Fetches the characters of a specific origin.
     *
     * @param locationId The unique identifier of the location.
     * @return Characters that plays from the specified location.
     */
    suspend fun getCharactersFrom(locationId: Int): List<Character>
}