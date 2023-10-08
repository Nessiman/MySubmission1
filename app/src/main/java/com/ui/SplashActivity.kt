package com.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.mysubmission1.R
import com.ui.main.MainActivity
import com.ui.main.SettingPreferences
import com.ui.main.ViewModelFactorySetting
import com.ui.main.dataStore

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        //untuk mengambil instansi settingspreference untuk mengelola preferensi pengaturan tema
        val pref = SettingPreferences.getInstance(applicationContext.dataStore)
        //untuk mendapatkan viewmodel ""SettingViewModel
        val settingViewModel = ViewModelProvider(
            this,
            ViewModelFactorySetting(pref)
        )[SettingViewModel::class.java]

        //untuk mengamati pengaturan tema aplikasi
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        //thread untuk menampilkan tampilan splash
        val splash = Thread {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
        splash.start()

    }
}