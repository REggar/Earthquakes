package com.reggar.earthquakes.earthquakelist.presentation.mvi

import com.reggar.earthquakes.earthquakelist.data.models.Earthquake

sealed class EarthquakeListEffect {

    data class NavigateToEarthquakeDetail(
        val earthquake: Earthquake
    ): EarthquakeListEffect()
}