package com.example.dicoding_bfaa1.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.dicoding_bfaa1.data.database.FavoriteUser
import com.example.dicoding_bfaa1.data.database.FavoriteUserDao
import com.example.dicoding_bfaa1.data.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUsers()

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute{ mFavoriteUserDao.insert(favoriteUser) }
    }

    fun delete(id: Int) {
        executorService.execute{ mFavoriteUserDao.removeFavorite(id) }
    }
}