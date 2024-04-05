package com.example.dicoding_bfaa1.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicoding_bfaa1.ui.favorite.FavoriteUserViewModel
import com.example.dicoding_bfaa1.ui.detail.DetailViewModel

@Suppress("UNCHECKED_CAST")
class FavoriteUserViewModelFactory(private val application: Application): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(application) as T
        } else if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown Viewmodel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteUserViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteUserViewModelFactory::class.java) {
                    INSTANCE = FavoriteUserViewModelFactory(application)
                }
            }
            return INSTANCE as FavoriteUserViewModelFactory
        }
    }
}