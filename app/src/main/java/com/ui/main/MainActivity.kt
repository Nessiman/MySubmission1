package com.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.data.response.ItemsItem
import com.example.mysubmission1.R
import com.example.mysubmission1.databinding.ActivityMainBinding
import com.ui.FavoriteActivity
import com.ui.SettingActivity
import com.ui.SettingViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactorySetting(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this){
            isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    mainViewModel.getItemUser(searchView.text.toString())
                    searchView.hide()
                    showLoading(true)
                    false

                }
        }



        mainViewModel.item.observe(this){item->
            setUserData(item)
        }

        mainViewModel.isloading.observe(this){
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }



    private fun setUserData(item: List<ItemsItem>){
        val adapter = UserAdapter(this@MainActivity)
        adapter.submitList(item)
        binding.rvUser.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.fav_menu -> {
                val favorite = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(favorite)
                return true
            }
            R.id.setting_menu -> {
                val setting = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(setting)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}