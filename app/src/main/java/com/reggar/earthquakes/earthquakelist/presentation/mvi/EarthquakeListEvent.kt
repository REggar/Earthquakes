package com.reggar.earthquakes.earthquakelist.presentation.mvi

import com.reggar.earthquakes.earthquakelist.data.models.Earthquake

sealed class EarthquakeListEvent {

    object OnInit: EarthquakeListEvent()

    data class OnEarthquakeClicked(
        val earthquake: Earthquake
    ): EarthquakeListEvent()
}