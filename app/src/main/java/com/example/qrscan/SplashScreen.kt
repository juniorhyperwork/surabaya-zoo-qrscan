package com.example.qrscan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        // Memberikan waktu 3 detik di splash screen
        Handler().postDelayed({
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }, 3000)
    }

}