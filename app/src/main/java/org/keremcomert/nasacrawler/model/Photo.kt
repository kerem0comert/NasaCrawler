package org.keremcomert.nasacrawler.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
        val id: Int,
        @SerializedName("img_src") val imgSrc: String,
        @SerializedName("earth_date") val earthDate: String,
        val camera: Camera,
        val rover: Rover
): Parcelable

@Parcelize
data class Camera(
        @SerializedName("full_name") val fullName: String
): Parcelable

@Parcelize
data class Rover(
        val name: String,
        @SerializedName("landing_date") val landingDate: String,
        @SerializedName("launch_date") val launchDate: String,
        val status: String
): Parcelable


