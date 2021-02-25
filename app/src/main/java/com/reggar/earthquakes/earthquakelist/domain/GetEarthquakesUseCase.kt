package com.reggar.earthquakes.earthquakelist.domain

import com.reggar.earthquakes.earthquakelist.data.EarthquakeRepository
import com.reggar.earthquakes.earthquakelist.data.models.Earthquake
import javax.inject.Inject

class GetEarthquakesUseCase @Inject constructor(
    private val earthquakeRepository: EarthquakeRepository
) {

    suspend operator fun invoke(): List<Earthquake> {
        return earthquakeRepository.getEarthquakes()
            .sortedByDescending { it.datetime }
    }
}