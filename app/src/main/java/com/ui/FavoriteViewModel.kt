package com.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.data.local.entity.FavoriteUser
import com.data.local.room.FavoriteUserDao
import com.data.local.room.FavoriteUserRoomDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDb: FavoriteUserRoomDatabase?
    private var userDao: FavoriteUserDao?

    init {
        userDb = FavoriteUserRoomDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}

