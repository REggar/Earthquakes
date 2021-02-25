package com.reggar.earthquakes.earthquakelist.data.datasources

import com.reggar.earthquakes.earthquakelist.data.models.Earthquake
import javax.inject.Inject

class MemoryDataSource @Inject constructor() {

    private var earthquakes: List<Earthquake>? = null

    fun getEarthquakes(): List<Earthquake>? {
        return earthquakes
    }

    fun setEarthquakes(newEarthquakes: List<Earthquake>) {
        earthquakes = newEarthquakes
    }
}