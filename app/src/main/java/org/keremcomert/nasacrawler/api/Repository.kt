package org.keremcomert.nasacrawler.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The repository that acts as a middle-ware between the requesters within the app and the
 * external API.
 */
@Singleton
class Repository @Inject constructor(private val sourceApi: SourceApi) {

    /**
     * The paging configs are specified here with 10 articles per page as instructed
     * in the coding test document.
     * @param selectedRover defines one of the three selected rovers to get photos of
     * @return A LiveData of PagingData, which mirrors the stream provided by Pager.flow,
     * but exposes it as a LiveData for the front-end to consume.
     */
    fun getResults(selectedRover: String) =
        Pager(config = PagingConfig(pageSize = 10, maxSize = 100, enablePlaceholders = false),
            pagingSourceFactory  = { PagingSource(sourceApi = sourceApi, selectedRover = selectedRover) })
            .liveData
}