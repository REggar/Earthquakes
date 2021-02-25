package com.reggar.earthquakes.earthquakelist.data

import com.reggar.earthquakes.earthquakelist.data.datasources.MemoryDataSource
import com.reggar.earthquakes.earthquakelist.data.datasources.RemoteDataSource
import com.reggar.earthquakes.earthquakelist.data.models.Earthquake
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EarthquakeRepository @Inject constructor(
    private val memoryDataSource: MemoryDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getEarthquakes(): List<Earthquake> {
        val cachedEarthquakes = memoryDataSource.getEarthquakes()
        if (cachedEarthquakes != null) {
            return cachedEarthquakes
        }
        val freshEarthquakes = remoteDataSource.getEarthquakes()
        memoryDataSource.setEarthquakes(freshEarthquakes)
        return freshEarthquakes
    }
}