package org.keremcomert.nasacrawler.api

import androidx.paging.PagingSource
import org.keremcomert.nasacrawler.model.Photo
import org.keremcomert.nasacrawler.util.API_KEY
import org.keremcomert.nasacrawler.util.INITIAL_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException




/**
 * @param sourceApi is the API requesting the pages. There is only one instance within this app
 * @param selectedRover The selectedRover can be either of three possible rovers in Mars
 * right now that we can crawl from: Curiosity, Opportunity, Spirit
 * @return PagingSource, with the requested articles retrieved from the API endpoint.
 */
class PagingSource(
    private val sourceApi: SourceApi,
    private val selectedRover: String
): PagingSource<Int, Photo>(){


    /**
     * @param params Includes the current key, needed to know which page the user
     * requests the function for.
     * The API defines different endpoints depending on specific queries, and within
     * the app we have two specific cases. One is the search query the user asks,
     *the other is any category selected. The urlToGet is dependent on this,
     * as explained in NASA API's documentation.
     *
     * @return The LoadResult, that contains the returned images and the keys
     * @exception HTTPException for 5** response codes.
     *
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val currentPage = params.key ?: INITIAL_PAGE_INDEX

        return try{
            val urlToGet = "$selectedRover/photos?sol=1000&api_key=$API_KEY"
            val response = sourceApi.getPhotos(urlToGet)

            LoadResult.Page(
                data = response.results,
                prevKey = if(currentPage == INITIAL_PAGE_INDEX) null else currentPage - 1,
                nextKey = if(response.results.isEmpty()) null else currentPage + 1
            )
        }catch (e: HttpException){
            LoadResult.Error(e)
        }catch (e: IOException){
            LoadResult.Error(e)
        }
    }
}