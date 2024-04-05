package com.example.dicoding_bfaa1.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.dicoding_bfaa1.data.response.GithubResponse
import com.example.dicoding_bfaa1.data.response.ItemsItem
import com.example.dicoding_bfaa1.data.retrofit.ApiConfig
import com.example.dicoding_bfaa1.ui.setting.SettingPreferences
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainViewModel(private val preferences: SettingPreferences): ViewModel() {
    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    init {
        findGithubUsers(USER_ID)
    }

    fun findGithubUsers(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUsers(query)
        client.enqueue(object: Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _userList.value = response.body()?.items
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val USER_ID = "hemas"
    }
}