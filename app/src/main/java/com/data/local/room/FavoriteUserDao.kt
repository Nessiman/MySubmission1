package com.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.data.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Query("DELETE FROM favoriteuser WHERE id = :userId")
    fun delete(userId: Int): Int

    @Query("SELECT * from favoriteuser ORDER BY id ASC")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT COUNT(*) FROM favoriteuser  WHERE id = :userId")
    fun isFavoriteUser(userId: Int): Int

}