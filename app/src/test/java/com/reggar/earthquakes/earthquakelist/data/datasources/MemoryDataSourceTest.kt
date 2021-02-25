package com.reggar.earthquakes.earthquakelist.data.datasources

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.reggar.earthquakes.earthquakelist.data.models.Earthquake
import io.mockk.mockk
import org.junit.Test

class MemoryDataSourceTest {
    private val earthquakes = listOf<Earthquake>(mockk())

    private val memoryDataSource = MemoryDataSource()

    @Test
    fun `getEarthquakes ifEmpty returnsNull`() {
        assertThat(memoryDataSource.getEarthquakes()).isNull()
    }

    @Test
    fun `getEarthquakes ifNotEmpty returnsEarthquakes`() {
        memoryDataSource.setEarthquakes(earthquakes)

        assertThat(memoryDataSource.getEarthquakes()).isEqualTo(earthquakes)
    }

}