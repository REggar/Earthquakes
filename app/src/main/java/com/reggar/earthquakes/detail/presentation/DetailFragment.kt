package com.reggar.earthquakes.detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.reggar.earthquakes.R
import com.reggar.earthquakes.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater).apply {
            mapviewDetail.onCreate(savedInstanceState)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapviewDetail.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        binding.mapviewDetail.onResume()
    }

    override fun onPause() {
        binding.mapviewDetail.onPause()
        super.onPause()
    }

    override fun onStop() {
        binding.mapviewDetail.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        _binding?.mapviewDetail?.onDestroy()
        _binding = null
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapviewDetail.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapviewDetail.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap) {
        val earthquakeLoc = LatLng(args.earthquake.lat, args.earthquake.lng)
        map.addMarker(
            MarkerOptions()
                .position(earthquakeLoc)
                .title(getString(R.string.earthquake_details_marker, args.earthquake.magnitude))
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(earthquakeLoc))
    }
}