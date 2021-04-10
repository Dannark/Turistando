package com.dannark.turistando.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dannark.turistando.database.Place
import com.dannark.turistando.databinding.ItemListRecommendedPlacesBinding

// This is ListAdapter is a RecyclerView, not a general ListView
class RecommendedPlacesAdapter(val clickListener: RecommendedPlaceListener):
        ListAdapter<Place, RecommendedPlacesAdapter.ViewHolder>(RecommendedPlacesDiffCallback()){
    val TAG = "RecommendedPlaces"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item =  getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: ItemListRecommendedPlacesBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Place, clickListener: RecommendedPlaceListener) {
            binding.rRlace = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListRecommendedPlacesBinding.inflate(layoutInflater, parent,
                        false)
                return ViewHolder(binding)
            }
        }
    }

}

class RecommendedPlacesDiffCallback : DiffUtil.ItemCallback<Place>(){
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }
}

class RecommendedPlaceListener(val clickListener: (placeId: Long) -> Unit){
    fun onClick(place: Place) = clickListener(place.id)
}