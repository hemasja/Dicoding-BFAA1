package com.example.dicoding_bfaa1.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicoding_bfaa1.database.FavoriteUser
import com.example.dicoding_bfaa1.databinding.ActivityFavoriteUserBinding
import com.example.dicoding_bfaa1.databinding.ItemUserListBinding
import com.example.dicoding_bfaa1.ui.detail.UserDetailActivity

class FavoriteUserAdapter: RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {
    private val favoriteUserList = ArrayList<FavoriteUser>()
    inner class FavoriteUserViewHolder(val binding: ItemUserListBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(items: FavoriteUser) {
            Glide.with(itemView.context)
                .load(items.avatarUrl)
                .circleCrop()
                .into(binding.civProfile)
            binding.tvName.text = items.login

            binding.listUser.setOnClickListener {
                val intent = Intent(it.context, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.USER_EXTRA, items.login)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun getItemCount(): Int = favoriteUserList.size

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        holder.binding(favoriteUserList[position])
    }

    fun setFavoriteUserList(favoriteUserList: List<FavoriteUser>) {
        val diffCallback = FavoriteDiffCallback(this.favoriteUserList, favoriteUserList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.favoriteUserList.clear()
        this.favoriteUserList.addAll(favoriteUserList)
        diffResult.dispatchUpdatesTo(this)
    }

}