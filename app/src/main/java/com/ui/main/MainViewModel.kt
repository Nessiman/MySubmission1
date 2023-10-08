package com.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.data.response.GithubResponse
import com.data.response.ItemsItem
import com.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

        class MainViewModel : ViewModel(){

            private val _items = MutableLiveData<List<ItemsItem>>()
            val item: LiveData<List<ItemsItem>> = _items

            private val _isLoading = MutableLiveData<Boolean>()
            val isloading: LiveData<Boolean> = _isLoading

            companion object{
                private const val TAG = "MainViewModel"
                private const val DEFAULT_NAME = "Nes"
            }

            init {
                getItemUser()
            }

           fun getItemUser(query: String = DEFAULT_NAME){
                _isLoading.value = true
                val client = ApiConfig.getApiService().getGithubUser(query)
            client.enqueue(object : Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _items.value = responseBody.items
                    }
                }else{
                    _isLoading.value = true
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.e(TAG, "Error:", t)
            }
        })
    }

}