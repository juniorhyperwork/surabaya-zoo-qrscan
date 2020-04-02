package com.example.qrscan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MapActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initListener()
    }

    fun initListener(){
        ivBack.setOnClickListener{
            onBackPressed()
        }
    }

}