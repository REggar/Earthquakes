package com.reggar.earthquakes.earthquakelist.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reggar.earthquakes.R
import com.reggar.earthquakes.databinding.ViewEarthquakeItemBinding
import com.reggar.earthquakes.earthquakelist.data.models.Earthquake
import kotlin.properties.Delegates

class EarthquakeListAdapter(
    private val context: Context,
    private val onEarthquakeClicked: (Earthquake) -> Unit,
) : RecyclerView.Adapter<EarthquakeListAdapter.ViewHolder>() {

    var items: List<Earthquake> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = ViewEarthquakeItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(
        private val binding: ViewEarthquakeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Earthquake) = with(binding) {
            textviewEarthquakeTime.text =
                    context.getString(R.string.earthquake_listitem_time, item.datetime)
            textviewEarthquakeLocation.text =
                    context.getString(R.string.earthquake_listitem_location, item.lat, item.lng, item.depth)
            textviewEarthquakeMagnitude.text =
                    context.getString(R.string.earthquake_listitem_magnitude, item.magnitude)
            root.setOnClickListener { onEarthquakeClicked(item) }
        }
    }
}