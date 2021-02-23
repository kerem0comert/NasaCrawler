package org.keremcomert.nasacrawler.api

import retrofit2.http.GET
import retrofit2.http.Url

interface SourceApi {
    /**
     * The GET method that interfaces with the API. It requires the suspend keyword as API
     * operations are asynchronous.
     * @param urlToGet depends on rover the user selected.
     * @return An instance of Response data class, that contains a List of returned photos.
     */
    @GET
    suspend fun getPhotos(@Url urlToGet: String): Response
}