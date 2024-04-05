package com.example.dicoding_bfaa1.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dicoding_bfaa1.R
import com.example.dicoding_bfaa1.data.response.UserDetailResponse
import com.example.dicoding_bfaa1.data.database.FavoriteUser
import com.example.dicoding_bfaa1.databinding.ActivityUserDetailBinding
import com.example.dicoding_bfaa1.ui.favorite.FavoriteUserActivity
import com.example.dicoding_bfaa1.ui.setting.SettingActivity
import com.example.dicoding_bfaa1.viewmodelfactory.FavoriteUserViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: DetailViewModel

    private var buttonState: Boolean = false
    private lateinit var favoriteUser: FavoriteUser
    private var userDetail: UserDetailResponse = UserDetailResponse()


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

        val application = FavoriteUserViewModelFactory.getInstance(this@UserDetailActivity.application)
        viewModel = ViewModelProvider(this@UserDetailActivity, application).get(DetailViewModel::class.java)

        val user = intent.extras!!.getString(USER_EXTRA)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        viewModel.getUserDetail(user!!)

        viewModel.detailList.observe(this) {
            setDisplayUser(it)
            setFavoriteUser(it)
        }

        setViewPager2(user)
    }

    private fun setFavoriteUser(userDetailResponse: UserDetailResponse) {
        userDetail = userDetailResponse
        setDisplayUser(userDetailResponse)

        favoriteUser = FavoriteUser(userDetailResponse.id!!, userDetailResponse.avatarUrl, userDetailResponse.login, userDetailResponse.htmlUrl)

        viewModel.getAllFavoriteUsers().observe(this) { favoriteUser ->
            if (favoriteUser != null) {
                for (data in favoriteUser) {
                    if (userDetailResponse.id == data.id) {
                        buttonState = true
                        binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                    }
                }
            }
        }

        binding.fabFavorite.setOnClickListener {
            if (!buttonState) {
                buttonState = true
                binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                insertDataToDatabase(userDetailResponse)
            } else {
                buttonState = false
                binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                viewModel.deleteFavoriteUser(userDetailResponse.id)
                Toast.makeText(this@UserDetailActivity, "User has been removed from your favorites!", Toast.LENGTH_SHORT).show()
            }
        }
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

    private fun insertDataToDatabase(userDetailList: UserDetailResponse) {
        favoriteUser.let { favoriteUser ->
            favoriteUser.id = userDetailList.id!!
            favoriteUser.avatarUrl = userDetailList.avatarUrl
            favoriteUser.login = userDetailList.login
            favoriteUser.htmlUrl = userDetailList.htmlUrl
            viewModel.insertFavoriteUser(favoriteUser)
            Toast.makeText(this@UserDetailActivity, "User has been added to your favorites!", Toast.LENGTH_SHORT).show()
        }
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

    fun goToFavorite(view: View) {
        val intent = Intent(this@UserDetailActivity, FavoriteUserActivity::class.java)
        startActivity(intent)
    }

    fun onBackButtonClicked(view: View) {
        onBackPressedDispatcher.onBackPressed()
    }

    fun goToSetting(view: View) {
        val intent = Intent(this@UserDetailActivity, SettingActivity::class.java)
        startActivity(intent)
    }
}