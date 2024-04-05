package com.example.dicoding_bfaa1.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicoding_bfaa1.databinding.ActivityFavoriteUserBinding
import com.example.dicoding_bfaa1.viewmodelfactory.FavoriteUserViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var viewModel: FavoriteUserViewModel
    private lateinit var adapter: FavoriteUserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = FavoriteUserViewModelFactory.getInstance(this@FavoriteUserActivity.application)
        viewModel = ViewModelProvider(this@FavoriteUserActivity, factory)[FavoriteUserViewModel::class.java]

        adapter = FavoriteUserAdapter()

        binding.rvFavoriteUser.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
        binding.rvFavoriteUser.setHasFixedSize(false)
        binding.rvFavoriteUser.adapter = this.adapter

        viewModel.getAllFavoriteUser().observe(this) { favoriteUser ->
            if (favoriteUser != null) {
                adapter.setFavoriteUserList(favoriteUser)
            }
        }
    }

    fun onBackButtonClicked(view: View) {
        onBackPressedDispatcher.onBackPressed()
    }
}