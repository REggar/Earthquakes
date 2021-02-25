package com.reggar.earthquakes.earthquakelist.data

import com.reggar.earthquakes.earthquakelist.data.models.GetEarthquakeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthquakeApi {

    @GET("earthquakesJSON")
    suspend fun getEarthquakes(
        @Query("north") north: Float,
        @Query("south") south: Float,
        @Query("east") east: Float,
        @Query("west") west: Float,
        @Query("username") username: String
    ): GetEarthquakeResponse
}