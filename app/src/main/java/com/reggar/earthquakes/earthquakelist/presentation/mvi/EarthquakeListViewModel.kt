package com.reggar.earthquakes.earthquakelist.presentation.mvi

import com.reggar.earthquakes.common.mvi.BaseMviViewModel
import com.reggar.earthquakes.earthquakelist.data.models.Earthquake
import com.reggar.earthquakes.earthquakelist.domain.GetEarthquakesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EarthquakeListViewModel @Inject constructor(
    private val getEarthquakes: GetEarthquakesUseCase
) : BaseMviViewModel<EarthquakeListState, EarthquakeListEvent, EarthquakeListEffect>(
    initialState = EarthquakeListState.Loading
) {

    override fun onEvent(event: EarthquakeListEvent) = when (event) {
        EarthquakeListEvent.OnInit -> loadEarthquakes()
        is EarthquakeListEvent.OnEarthquakeClicked -> onEarthquakeClicked(event.earthquake)
    }

    private fun loadEarthquakes() {
        emitState(EarthquakeListState.Loading)
        launch {
            try {
                val earthquakes = getEarthquakes()
                emitState(EarthquakeListState.Content(earthquakes))
            } catch (exception: Exception) {
                emitState(EarthquakeListState.Error)
            }
        }
    }

    private fun onEarthquakeClicked(earthquake: Earthquake) {
        emitEffect(EarthquakeListEffect.NavigateToEarthquakeDetail(earthquake))
    }
}