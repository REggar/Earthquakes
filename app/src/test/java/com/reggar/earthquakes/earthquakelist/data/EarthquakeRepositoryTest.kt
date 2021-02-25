package com.reggar.earthquakes.earthquakelist.data

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.reggar.earthquakes.earthquakelist.data.datasources.MemoryDataSource
import com.reggar.earthquakes.earthquakelist.data.datasources.RemoteDataSource
import com.reggar.earthquakes.earthquakelist.data.models.Earthquake
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class EarthquakeRepositoryTest {

    private val earthquakes = mockk<List<Earthquake>>()
    private val memoryDataSource = mockk<MemoryDataSource>(relaxUnitFun = true)
    private val remoteDataSource = mockk<RemoteDataSource>()

    private val repository = EarthquakeRepository(memoryDataSource, remoteDataSource)

    @Test
    fun `getEarthquakes ifHasMemoryData returnsMemoryData`() = runBlocking {
        coEvery { memoryDataSource.getEarthquakes() }.returns(earthquakes)

        assertThat(repository.getEarthquakes()).isEqualTo(earthquakes)
    }

    @Test
    fun `getEarthquakes ifNoMemoryData returnsRemoteData`() = runBlocking {
        coEvery { memoryDataSource.getEarthquakes() }.returns(null)
        coEvery { remoteDataSource.getEarthquakes() }.returns(earthquakes)

        assertThat(repository.getEarthquakes()).isEqualTo(earthquakes)
    }

    @Test
    fun `getEarthquakes ifReturnsRemoteData cacheInMemory`() = runBlocking {
        coEvery { memoryDataSource.getEarthquakes() }.returns(null)
        coEvery { remoteDataSource.getEarthquakes() }.returns(earthquakes)

        repository.getEarthquakes()

        verify { memoryDataSource.setEarthquakes(earthquakes) }
    }
}
