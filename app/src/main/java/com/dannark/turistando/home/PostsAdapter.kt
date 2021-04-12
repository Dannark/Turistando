package com.dannark.turistando.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dannark.turistando.database.Post
import com.dannark.turistando.databinding.ItemListPostBinding

class PostsAdapter(val clickListener: PostsListener)
    : ListAdapter<Post, PostsAdapter.ViewHolder>(PostsAdapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
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

class PostsAdapterDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}

class PostsListener(val clickListener: (postId: Long, buttonId:String) -> Unit) {
    fun onLikeClick(post: Post) = clickListener(post.postId, "like")
    fun onShareClick(post: Post) = clickListener(post.postId, "share")
}
