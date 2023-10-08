package com.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.data.local.entity.FavoriteUser
import com.data.response.ItemsItem
import com.example.mysubmission1.R
import com.example.mysubmission1.databinding.ActivityFavoriteBinding
import com.ui.detail.UserDetailActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)


        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorites Users"
        favoriteAdapter =
            FavoriteAdapter()
        favoriteAdapter.notifyDataSetChanged()

        //untuk membuat instance dari FavoriteViewModel di activity
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        //untuk callback ketika daftar item diklik
        favoriteAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.USER_NAME, data.login)
                startActivity(intent)
            }
        })

        binding.apply {
            rvFavUser.setHasFixedSize(true)
            rvFavUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavUser.adapter = favoriteAdapter
        }

        favoriteViewModel.getFavoriteUser()?.observe(this){
            if (it != null){
                val list = favList(it)
                favoriteAdapter.setList(list)
            }
        }
    }

    private fun favList(it: List<FavoriteUser>): ArrayList<ItemsItem> {
        val listusers = ArrayList<ItemsItem>()
        for (user in it){
            val userFav = ItemsItem(
                user.id,
                user.login,
                user.avatar_url ?: ""
            )
            listusers.add(userFav)
            }
        return listusers
    }
}