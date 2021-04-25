package com.dannark.turistando.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dannark.turistando.R
import com.dannark.turistando.databinding.ItemListSearchSuggestionBinding
import com.dannark.turistando.domain.Suggestion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class SearchAdapter(val clickListener: SuggestionListener)
    : ListAdapter<DataItem, RecyclerView.ViewHolder>(SearchAdapterDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            ITEM_VIEW_TYPE_HEADER -> SuggestionItemViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.SuggestionItem -> ITEM_VIEW_TYPE_ITEM
            is DataItem.HeaderItem -> ITEM_VIEW_TYPE_HEADER
        }
    }

    fun addHeaderAndSubmitList(list: List<Suggestion>){
        adapterScope.launch {
            val items = when(list){
                null -> listOf(DataItem.HeaderItem)
                else -> listOf(DataItem.HeaderItem) + list.map { DataItem.SuggestionItem (it) }
            }
            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val item = getItem(position) as DataItem.SuggestionItem
                holder.bind(item.search, clickListener)
            }
        }
    }

    class ViewHolder private constructor(val binding: ItemListSearchSuggestionBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Suggestion, clickListener: SuggestionListener){
            binding.suggestion = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListSearchSuggestionBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class SearchAdapterDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

class SuggestionListener(val clickListener: (id: String) -> Unit) {
    fun onLikeClick(search: Suggestion) = clickListener(search.id)
}

sealed class DataItem {
    data class SuggestionItem(val search: Suggestion): DataItem(){
        override val id = search.id
    }

    object HeaderItem: DataItem(){
        override val id = Long.MIN_VALUE.toString()
    }

    abstract val id: String
}

class SuggestionItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): SuggestionItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_list_search_title, parent, false)
            return SuggestionItemViewHolder(view)
        }
    }
}