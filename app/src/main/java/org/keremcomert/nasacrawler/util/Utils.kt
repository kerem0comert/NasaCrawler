package org.keremcomert.nasacrawler.util

import org.keremcomert.nasacrawler.BuildConfig


const val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/"
const val INITIAL_PAGE_INDEX = 1
const val API_KEY = BuildConfig.NASA_KEY
const val CURRENT_ROVER = "currentRover"
const val ROVER_CURIOSITY = "curiosity"
const val ROVER_OPPORTUNITY = "opportunity"
const val ROVER_SPIRIT = "spirit"