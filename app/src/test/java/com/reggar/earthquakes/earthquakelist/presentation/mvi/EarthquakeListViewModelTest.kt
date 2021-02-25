package com.reggar.earthquakes.earthquakelist.presentation.mvi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.reggar.earthquakes.common.neverReturns
import com.reggar.earthquakes.common.MainCoroutineRule
import com.reggar.earthquakes.earthquakelist.data.models.Earthquake
import com.reggar.earthquakes.earthquakelist.domain.GetEarthquakesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class EarthquakeListViewModelTest {
    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule val coroutineRule = MainCoroutineRule()

    private val getEarthquakesUseCase = mockk<GetEarthquakesUseCase>()

    private val viewModel by lazy {
        EarthquakeListViewModel(getEarthquakesUseCase)
    }

    @Test
    fun `onEmpty emitLoadingState`() {
        assertLatestState().isEqualTo(EarthquakeListState.Loading)
    }

    @Test
    fun `onStart whileAwaitGetEarthquakes emitLoadingState`() {
        coEvery { getEarthquakesUseCase() }.neverReturns()

        viewModel.onEvent(EarthquakeListEvent.OnInit)

        assertLatestState().isEqualTo(EarthquakeListState.Loading)
    }

    @Test
    fun `onStart onSuccessfulGetEarthquakes emitContentState`() {
        coEvery { getEarthquakesUseCase() }.returns(listOf())

        viewModel.onEvent(EarthquakeListEvent.OnInit)

        assertLatestState().isEqualTo(EarthquakeListState.Content(emptyList()))
    }

    @Test
    fun `onStart onErrorGetEarthquakes emitErrorState`() {
        coEvery { getEarthquakesUseCase() }.throws(IOException())

        viewModel.onEvent(EarthquakeListEvent.OnInit)

        assertLatestState().isEqualTo(EarthquakeListState.Error)
    }

    @Test
    fun `onEarthquakeClicked emitNavigateToEarthquakeDetailEffect`() {
        val earthquake = mockk<Earthquake>()

        viewModel.onEvent(EarthquakeListEvent.OnEarthquakeClicked(earthquake))

        assertLatestEffect().isEqualTo(EarthquakeListEffect.NavigateToEarthquakeDetail(earthquake))
    }

    private fun assertLatestState() = assertThat(viewModel.state.value)

    private fun assertLatestEffect() = assertThat(viewModel.effect.value)
}
