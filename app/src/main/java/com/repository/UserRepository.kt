//package com.repository
//
//import androidx.lifecycle.LiveData
//import com.data.local.entity.FavoriteUser
//import com.data.local.room.FavoriteUserDao
//
//class UserRepository(private val userDao : FavoriteUserDao) {
//    fun getFavoriteUser(): LiveData<List<FavoriteUser>> {
//        return userDao.getFavoriteUser()
//    }
//    fun setFavorite(username: String, avatar : String, id: Int){
//        val user = FavoriteUser(id, username, avatar)
//        userDao.insert(user)
//    }
//    fun deleteFavorite(id: Int){
//        userDao.delete(id)
//    }
//
//    fun check(id: Int): Int{
//        return userDao.check(id)
//    }
//
//    companion object{
//        var username = "KEY_DATA"
//
//        @Volatile
//        private var instance: UserRepository? = null
//        fun getInstance(
//            userDao: FavoriteUserDao
//        ) : UserRepository = instance ?: synchronized(this){
//            instance ?: UserRepository(userDao)
//        }.also { instance = it}
//    }
//}