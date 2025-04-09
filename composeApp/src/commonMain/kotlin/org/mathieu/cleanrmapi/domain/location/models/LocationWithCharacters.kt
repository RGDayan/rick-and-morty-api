package org.mathieu.cleanrmapi.domain.location.models

import org.mathieu.cleanrmapi.domain.character.models.Character

/**
 * Represents a location and its resident characters.
 *
 * Combines location details with a list of residing characters.
 * It allows the domain ot create a location with a specific list of residents.
 *
 * @property id Unique identifier of the location.
 * @property name Name of the location.
 * @property type Type of the location (e.g., "Planet").
 * @property dimension Dimension of the location.
 * @property residents List of characters residing in this location.
 *
 * The constructor takes a [Location] object and a list of [Character] objects as parameter
 * to distribute the data.
 */
data class LocationWithCharacters (
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<Character>
) {
    constructor(location: Location, residents: List<Character>) : this(
        id = location.id,
        name = location.name,
        type = location.type,
        dimension = location.dimension,
        residents = residents
    )
}