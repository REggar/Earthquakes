package com.reggar.earthquakes.earthquakelist.domain

import assertk.all
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.hasSize
import com.reggar.earthquakes.earthquakelist.data.EarthquakeRepository
import com.reggar.earthquakes.earthquakelist.data.models.Earthquake
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetEarthquakesUseCaseTest {
    private val earthquake1 =
        Earthquake(
            eqid="us60003sc0",
            datetime="2019-05-26 07:41:44",
            depth=109.88,
            lat=-5.796,
            lng=-75.2975,
            src="us",
            magnitude=8.0
        )
    private val earthquake2 =
        Earthquake(
            eqid="us60003sc0",
            datetime="2012-05-26 07:41:44",
            depth=109.88,
            lat=-5.796,
            lng=-75.2975,
            src="us",
            magnitude=8.0
        )
    private val earthquakes = listOf(earthquake1, earthquake2)

    private val earthquakeRepository = mockk<EarthquakeRepository>()
    private val useCase = GetEarthquakesUseCase(earthquakeRepository)

    @Before
    fun setup() {
        coEvery { earthquakeRepository.getEarthquakes() } returns earthquakes
    }

    @Test
    fun `getEarthquakesUseCase returnsSortedByDateList`() = runBlocking {
        assertThat(useCase()).all {
            hasSize(2)
            containsExactly(earthquake1, earthquake2)
        }
    }
}
