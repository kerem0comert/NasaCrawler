package org.keremcomert.nasacrawler.api

import org.keremcomert.nasacrawler.model.Photo

data class Response(
    val photos: List<Photo>
)
