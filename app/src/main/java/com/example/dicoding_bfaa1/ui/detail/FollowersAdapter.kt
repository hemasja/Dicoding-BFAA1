package com.example.dicoding_bfaa1.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicoding_bfaa1.data.response.ItemsItem
import com.example.dicoding_bfaa1.databinding.ItemUserListBinding

class FollowersAdapter: ListAdapter<ItemsItem, FollowersAdapter.FollowersViewHolder>(DIFF_CALLBACK) {
    class FollowersViewHolder(val binding: ItemUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(item: ItemsItem) {
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .circleCrop()
                .into(binding.civProfile)
            binding.tvName.text = item.login
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val items = getItem(position)
        holder.binding(items)
    }
}