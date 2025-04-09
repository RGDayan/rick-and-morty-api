package org.mathieu.cleanrmapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.mathieu.cleanrmapi.data.local.objects.LocationObject

/**
 * Data Access Object for the [LocationObject] entity.
 *
 * This interface provides methods to interact with the location data in the database.
 * It uses Room's annotations to define database operations.
 */
@Dao
interface LocationDAO {

    /**
     * Retrieves a location by its ID.
     *
     * @param id The ID of the location.
     * @return The LocationObject with the given ID, or null if not found.
     */
    @Query("select * from ${RMDatabase.LOCATION_TABLE} where id = :id")
    suspend fun getLocation(id: Int): LocationObject?

    /**
     * Inserts or replaces a [LocationObject] in the database.
     *
     * @param location The [LocationObject] to insert or replace.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationObject)
}