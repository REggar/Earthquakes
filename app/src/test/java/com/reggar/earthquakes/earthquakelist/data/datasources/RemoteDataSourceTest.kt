package com.reggar.earthquakes.earthquakelist.data.datasources

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.reggar.earthquakes.common.data.Environment
import com.reggar.earthquakes.earthquakelist.data.EarthquakeApi
import com.reggar.earthquakes.earthquakelist.data.models.Earthquake
import com.reggar.earthquakes.earthquakelist.data.models.GetEarthquakeResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RemoteDataSourceTest {

    private var environment = Environment("http", "testuser")
    private val earthquakes = listOf<Earthquake>(mockk())
    private val getEarthquakeResponse = mockk<GetEarthquakeResponse> {
        every { earthquakes } returns this@RemoteDataSourceTest.earthquakes
    }
    private val earthquakeApi = mockk<EarthquakeApi>()

    private val remoteDataSource = RemoteDataSource(earthquakeApi, environment)

    @Test
    fun `getEarthquakes forHardcodedLocation returnsEarthquakesFromResponse`() = runBlocking {
        coEvery { earthquakeApi.getEarthquakes(
            north = 44.1f,
            south = -9.9f,
            east = -22.4f,
            west = 55.2f,
            username = "testuser"
        ) }.returns(getEarthquakeResponse)

        assertThat(remoteDataSource.getEarthquakes()).isEqualTo(earthquakes)
    }
}
