package com.reggar.earthquakes.earthquakelist.data.datasources

import com.reggar.earthquakes.common.data.Environment
import com.reggar.earthquakes.earthquakelist.data.EarthquakeApi
import com.reggar.earthquakes.earthquakelist.data.models.Earthquake
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val earthquakeApi: EarthquakeApi,
    private val environment: Environment
) {

    suspend fun getEarthquakes(): List<Earthquake> {
        val earthquakeResponse = earthquakeApi.getEarthquakes(
            north = 44.1f,
            south = -9.9f,
            east = -22.4f,
            west = 55.2f,
            username = environment.username
        )
        return earthquakeResponse.earthquakes
    }
}