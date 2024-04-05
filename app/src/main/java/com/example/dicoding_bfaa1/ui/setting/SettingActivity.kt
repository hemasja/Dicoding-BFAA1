package com.example.dicoding_bfaa1.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.dicoding_bfaa1.databinding.ActivitySettingBinding
import com.example.dicoding_bfaa1.ui.MainActivity
import com.example.dicoding_bfaa1.viewmodelfactory.ThemeViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: SettingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = SettingPreferences.getInstance(application.datastore)
        viewModel = ViewModelProvider(this, ThemeViewModelFactory(preferences)).get(
            SettingViewModel::class.java
        )

        changeTheme(viewModel)
    }

    private fun changeTheme(viewModel: SettingViewModel) {
        val changeTheme = binding.smTheme

        this.viewModel.getThemeSetting().observe(this@SettingActivity) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                changeTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                changeTheme.isChecked = false
            }

            changeTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                this.viewModel.saveThemeSetting(isChecked)
            }
        }
    }

    fun onBackButtonClicked(view: View) {
        onBackPressedDispatcher.onBackPressed()
    }
}