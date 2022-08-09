package com.example.catapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.catapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}