package com.example.dicoding_bfaa1.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicoding_bfaa1.ui.home.MainViewModel
import com.example.dicoding_bfaa1.ui.setting.SettingPreferences
import com.example.dicoding_bfaa1.ui.setting.SettingViewModel

@Suppress("UNCHECKED_CAST")
class ThemeViewModelFactory(private val preferences: SettingPreferences): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(preferences) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel Class: ${modelClass.name}")
    }
}