package com.reggar.earthquakes.earthquakelist.presentation.mvi

import com.reggar.earthquakes.earthquakelist.data.models.Earthquake

sealed class EarthquakeListState {

    object Loading: EarthquakeListState()

    data class Content(
        val earthquakes: List<Earthquake>
    ): EarthquakeListState()

    object Error: EarthquakeListState()
}