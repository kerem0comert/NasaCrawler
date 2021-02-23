package org.keremcomert.nasacrawler.model

import com.google.gson.annotations.SerializedName

data class Photo(
        val id: Int,
        @SerializedName("img_src") val imgSrc: String,
        @SerializedName("earth_date") val earthDate: String,
)

data class Camera(
        @SerializedName("full_name") val fullName: String
)

data class Rover(
        val name: String,
        @SerializedName("landing_date") val landingDate: String,
        @SerializedName("launc_date") val launchDate: String,
        val status: String
)


