package com.reggar.earthquakes.earthquakelist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.reggar.earthquakes.databinding.FragmentEarthquakeListBinding
import com.reggar.earthquakes.earthquakelist.data.models.Earthquake
import com.reggar.earthquakes.earthquakelist.presentation.adapter.EarthquakeListAdapter
import com.reggar.earthquakes.earthquakelist.presentation.mvi.EarthquakeListEffect
import com.reggar.earthquakes.earthquakelist.presentation.mvi.EarthquakeListEvent
import com.reggar.earthquakes.earthquakelist.presentation.mvi.EarthquakeListState
import com.reggar.earthquakes.earthquakelist.presentation.mvi.EarthquakeListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EarthquakeListFragment : Fragment() {
    private val viewModel: EarthquakeListViewModel by viewModels()

    private var _binding: FragmentEarthquakeListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: EarthquakeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthquakeListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = EarthquakeListAdapter(requireContext()) {
            viewModel.onEvent(EarthquakeListEvent.OnEarthquakeClicked(it))
        }
        binding.recyclerviewEarthquakelist.adapter = adapter
        binding.recyclerviewEarthquakelist.layoutManager = LinearLayoutManager(requireContext())
        viewModel.state.observe(viewLifecycleOwner, ::render)
        viewModel.effect.observe(viewLifecycleOwner, ::handleEffect)
        if (savedInstanceState == null) {
            viewModel.onEvent(EarthquakeListEvent.OnInit)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun render(state: EarthquakeListState) = when (state) {
        is EarthquakeListState.Content -> {
            binding.progressbarEarthquakelist.isVisible = false
            binding.recyclerviewEarthquakelist.isVisible = true
            binding.textviewEarthquakelistError.isVisible = false
            adapter.items = state.earthquakes
        }
        EarthquakeListState.Error -> {
            binding.progressbarEarthquakelist.isVisible = false
            binding.recyclerviewEarthquakelist.isVisible = false
            binding.textviewEarthquakelistError.isVisible = true
        }
        EarthquakeListState.Loading -> {
            binding.progressbarEarthquakelist.isVisible = true
            binding.recyclerviewEarthquakelist.isVisible = false
            binding.textviewEarthquakelistError.isVisible = false
        }
    }

    private fun handleEffect(effect: EarthquakeListEffect?) = when (effect) {
        is EarthquakeListEffect.NavigateToEarthquakeDetail -> {
            navigateToEarthquakeDetail(effect.earthquake)
        }
        else -> throw IllegalStateException("Unsupported effect: $effect")
    }

    private fun navigateToEarthquakeDetail(earthquake: Earthquake) {
        val action = EarthquakeListFragmentDirections.actionToDetailFragment(earthquake)
        findNavController().navigate(action)
    }
}
