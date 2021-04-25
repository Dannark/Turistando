package com.dannark.turistando.home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dannark.turistando.R
import com.dannark.turistando.databinding.ItemListPostBinding
import com.dannark.turistando.domain.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_ADS = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

/**
 * @author Daniel Queiroz
 *
 * An Adapter that provides a list of [Post] or [Header] to a RecyclerView
 * This Adapter uses a design pattern call Adapter Pattern
 * Setting [data] will cause the displayed list to update.
 */
class PostsAdapter(val clickListener: PostsListener)
    : ListAdapter<DataItem, RecyclerView.ViewHolder>(PostsAdapterDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            ITEM_VIEW_TYPE_ADS -> TextViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.PostItem -> ITEM_VIEW_TYPE_ITEM
            is DataItem.Ads -> ITEM_VIEW_TYPE_ADS
        }
    }

    fun addHeaderAndSubmitList(list: List<Post>){
        val dataList = list.map { DataItem.PostItem(it) }
        val newList = mutableListOf<DataItem>()

        for ((i, value ) in dataList.withIndex()){
            newList.add(value)
            if (i % 10 == 0 && i != 0){
                newList.add(DataItem.Ads)
            }
        }

        adapterScope.launch {
            val items = when(list){
                null -> listOf(DataItem.Ads)
                else -> newList//list.map { DataItem.PostItem(it) } + listOf(DataItem.Header)
            }
            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val postItem = getItem(position) as DataItem.PostItem
                holder.bind(postItem.post, clickListener)
            }
        }
    }

    class ViewHolder private constructor(val binding: ItemListPostBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post, clickListener: PostsListener){
            binding.post = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListPostBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PostsAdapterDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

class PostsListener(val clickListener: (postId: Long, buttonId:String) -> Unit) {
    fun onLikeClick(post: Post) = clickListener(post.postId, "like")
    fun onShareClick(post: Post) = clickListener(post.postId, "share")
}

sealed class DataItem {
    data class PostItem(val post: Post): DataItem(){
        override val id = post.postId
    }

    object Ads: DataItem(){
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}

class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): TextViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_list_ads, parent, false)
            return TextViewHolder(view)
        }
    }
}