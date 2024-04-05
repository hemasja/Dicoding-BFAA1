package com.example.dicoding_bfaa1.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicoding_bfaa1.data.response.UserDetailResponse
import com.example.dicoding_bfaa1.data.retrofit.ApiConfig
import com.example.dicoding_bfaa1.data.database.FavoriteUser
import com.example.dicoding_bfaa1.data.repository.FavoriteUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {
    private val _detailList = MutableLiveData<UserDetailResponse>()
    val detailList: LiveData<UserDetailResponse> = _detailList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mFavoriteRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getUserDetail(user: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUserDetail(user)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _detailList.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteRepository.getAllFavoriteUsers()

    fun insertFavoriteUser(user: FavoriteUser) {
        mFavoriteRepository.insert(user)
    }

    fun deleteFavoriteUser(id: Int) {
        mFavoriteRepository.delete(id)
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}