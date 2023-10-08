package com.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.data.local.entity.FavoriteUser
import com.data.local.room.FavoriteUserDao
import com.data.local.room.FavoriteUserRoomDatabase
import com.data.response.DetailUserResponse
import com.data.response.FollowersAndFollowingItem
import com.data.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        private const val TAG = "Detail User"
    }

   private val _user = MutableLiveData<DetailUserResponse?>()
    val user = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userFollowers = MutableLiveData<List<FollowersAndFollowingItem>>()
    val userFollowers: LiveData<List<FollowersAndFollowingItem>> = _userFollowers

    private val _userFollowing = MutableLiveData<List<FollowersAndFollowingItem>>()
    val  userFollowing: MutableLiveData<List<FollowersAndFollowingItem>> = _userFollowing

    private var userDb: FavoriteUserRoomDatabase?
    private var userDao: FavoriteUserDao?

    init {
        userDb = FavoriteUserRoomDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    private val _isFavoriteUser = MutableLiveData<Boolean>()
    val isFavoriteUser: LiveData<Boolean> = _isFavoriteUser


    fun getDetailUser(username : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null){
                        _user.value = responseBody
                        val userId = responseBody.id
                        checkFavoriteStatus(userId)
                        Log.d(TAG, response.toString())
                    }
                }else{
                    _isLoading.value = true
                    Log.d(TAG, response.message())
                }

            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "${t.message}")
            }
        })
    }

    fun getFollowersUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<FollowersAndFollowingItem>> {
            override fun onResponse(
                call: Call<List<FollowersAndFollowingItem>>,
                response: Response<List<FollowersAndFollowingItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userFollowers.value = responseBody!!
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowersAndFollowingItem>>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "$t.message")
            }
        })
    }


    fun getFollowingUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object  : Callback<List<FollowersAndFollowingItem>>{
            override fun onResponse(
                call: Call<List<FollowersAndFollowingItem>>,
                response: Response<List<FollowersAndFollowingItem>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userFollowing.value = responseBody!!
                        Log.d("FOLLOWING", responseBody.toString())
                    }
                } else {
                    _isLoading.value = true
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<List<FollowersAndFollowingItem>>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "$t.message")
            }

        })
    }

    fun addFavUser(id: Int, username: String, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                id, username, avatarUrl
            )
            userDao?.insert(user)
            _isFavoriteUser.postValue(true)
        }
    }

    fun deleteFavUser(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.delete(id)
            _isFavoriteUser.postValue(false)
        }
    }


    private fun checkFavoriteStatus(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val isFavorite = userDao?.isFavoriteUser(userId) ?: 0>0
            _isFavoriteUser.postValue(isFavorite)
        }
    }
}
