package org.mathieu.cleanrmapi.domain.location.models

/**
 * Represents a preview of a location in the Rick and Morty API.
 * This class is used to display basic information about a location without including
 * the full details of a character's location
 *
 * @property id The unique identifier for the location.
 * @property name The name of the location.
 */
data class LocationPreview(
    val id: Int,
    val name: String
)