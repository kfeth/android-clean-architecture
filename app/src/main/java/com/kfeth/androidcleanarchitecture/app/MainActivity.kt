package com.kfeth.androidcleanarchitecture.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kfeth.androidcleanarchitecture.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}