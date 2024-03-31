@file:Suppress("DEPRECATION")

package com.example.dicoding_bfaa1.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.dicoding_bfaa1.R
import com.example.dicoding_bfaa1.data.response.ItemsItem
import com.example.dicoding_bfaa1.data.response.UserDetailResponse
import com.example.dicoding_bfaa1.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    companion object {
        const val USER_EXTRA = "user_extra"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<ItemsItem>(USER_EXTRA)
        val currentUser = user?.login.toString()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        viewModel.getUserDetail(currentUser)

        viewModel.detailList.observe(this) {
            setDisplayUser(it)
        }

        setViewPager2(currentUser)
    }

    private fun setDisplayUser(userList: UserDetailResponse) {
        Glide.with(this@UserDetailActivity)
            .load(userList.avatarUrl)
            .circleCrop()
            .into(binding.civProfile)
        binding.tvDetailName.text = userList.name
        binding.tvUsername.text = userList.login
        binding.tvFollowers.text = "${userList.followers} Followers"
        binding.tvFollowing.text = "${userList.following} Following"
    }

    private fun setViewPager2(currentUser: String) {
        val pagerSectionAdapter = PagerSectionAdapter(this)

        pagerSectionAdapter.username = currentUser
        binding.vpFollow.adapter = pagerSectionAdapter
        TabLayoutMediator(binding.tlFollow, binding.vpFollow) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }
}