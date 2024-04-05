package com.example.dicoding_bfaa1.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.dicoding_bfaa1.data.response.ItemsItem
import com.example.dicoding_bfaa1.databinding.ItemUserListBinding

class UserAdapter(private val userList: List<ItemsItem>) : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onClickedItemCallback: OnClickedItemCallback

    class MyViewHolder(private val binding: ItemUserListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: ItemsItem) {
            Glide.with(itemView.context)
                .load(items.avatarUrl)
                .circleCrop()
                .into(binding.civProfile)
            binding.tvName.text = items.login
        }
    }

    interface OnClickedItemCallback {
        fun onClickedItem(data: ItemsItem)
    }

    fun setOnClickedItemCallback(onClickedItemCallback: OnClickedItemCallback) {
        this.onClickedItemCallback = onClickedItemCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)

        holder.itemView.setOnClickListener {
            this.onClickedItemCallback.onClickedItem(userList[holder.adapterPosition])
        }
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}