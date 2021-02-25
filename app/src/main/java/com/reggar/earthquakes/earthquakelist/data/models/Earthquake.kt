package com.reggar.earthquakes.earthquakelist.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Earthquake(
    val eqid: String,
    val datetime: String,
    val depth: Double,
    val lat: Double,
    val lng: Double,
    val src: String,
    val magnitude: Double,
): Parcelable