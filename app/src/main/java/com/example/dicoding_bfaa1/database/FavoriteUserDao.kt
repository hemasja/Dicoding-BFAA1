package com.example.dicoding_bfaa1.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Query("SELECT * from FavoriteUser ORDER BY login ASC")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>

    @Query("SELECT * from FavoriteUser WHERE FavoriteUser.id = :id")
    fun checkingUser(id: Int): LiveData<FavoriteUser>

    @Query("DELETE from FavoriteUser WHERE FavoriteUser.id = :id")
    fun removeFavorite(id: Int)

}