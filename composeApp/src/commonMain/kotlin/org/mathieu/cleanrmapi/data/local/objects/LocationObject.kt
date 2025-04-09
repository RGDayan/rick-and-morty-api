package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.extensions.extractIdsFromUrls
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.location.models.Location

/**
 * Represents a location in the Rick and Morty universe.
 *
 * Stored as an entity in the Room database.
 *
 * @property id Unique identifier. (Primary Key)
 * @property name Location name (e.g., "Citadel of Ricks").
 * @property type Location type (e.g., "Space station").
 * @property dimension The dimension (e.g., "Dimension C-137").
 * @property residentsIds Comma-separated IDs of resident characters (e.g., "1,2,3").
 *
 * @see RMDatabase.LOCATION_TABLE
 * @see MustBeCommaSeparatedIds
 */
@Entity(tableName = RMDatabase.LOCATION_TABLE)
class LocationObject (
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    @MustBeCommaSeparatedIds
    val residentsIds: String
)

/**
 * Converts a [LocationObject] to a detailed [Location] model with resident details.
 *
 * @param idsToCharactersConverter A function to fetch [Character] details from a comma-separated string of resident IDs.
 *                                 Defaults to returning an empty list (no resident details).
 * @return A [Location] object with basic info and a list of resident [Character] objects.
 */
internal suspend fun LocationObject.toDetailedModel(
    idsToCharactersConverter: suspend (residentsIds: String) -> List<Character> = { emptyList() }
) = Location (
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = idsToCharactersConverter(residentsIds)
)

/**
 * Converts a [LocationResponse] to a [LocationObject] for database storage.
 *
 * Extracts ID, name, type, dimension, and resident IDs from the response.
 *
 * @return A [LocationObject] ready for database insertion.
 * @see LocationResponse
 * @see LocationObject
 */
internal fun LocationResponse.toDBObject() = LocationObject (
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentsIds = residents.extractIdsFromUrls()
)