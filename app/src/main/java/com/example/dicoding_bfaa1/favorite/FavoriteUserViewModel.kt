package com.example.dicoding_bfaa1.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dicoding_bfaa1.database.FavoriteUser
import com.example.dicoding_bfaa1.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application): ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUsers()
}