package com.dannark.turistando.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dannark.turistando.databinding.ItemListFriendsBinding
import com.dannark.turistando.domain.Friend

class FriendsAdapter(val clickListener: FriendClickListener):
        ListAdapter<Friend, FriendsAdapter.ViewHolder>(FriendsDiffCallback()) {

    class ViewHolder(val binding: ItemListFriendsBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Friend, clickListener: FriendClickListener){
            binding.friend = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListFriendsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class FriendClickListener(val clickListener: (friend: Friend) -> Unit) {
    fun onClick(friend: Friend) = clickListener(friend)
}

class FriendsDiffCallback: DiffUtil.ItemCallback<Friend>() {
    override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
        return oldItem.friendId == newItem.friendId
    }

    override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
        return oldItem == newItem
    }
}
