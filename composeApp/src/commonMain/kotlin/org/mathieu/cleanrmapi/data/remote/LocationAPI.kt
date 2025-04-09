package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse


/**
 * `LocationApi` is a class responsible for interacting with the location-related API endpoints.
 * It provides methods to fetch location information by a given ID.
 *
 * @property client The [HttpClient] instance used for making HTTP requests.
 */
internal class LocationApi(private val client: HttpClient) {

    /**
     * Retrieves location details by ID.
     *
     * Makes a GET request to "location/{id}".
     *
     * @param id The location ID.
     * @return Location details, or null if the request fails or data is invalid.
     * @throws Exception if the request fails due to network issues.
     */
    suspend fun getLocation(id: Int): LocationResponse? = client
        .get("location/$id")
        .accept(HttpStatusCode.OK)
        .body()

}