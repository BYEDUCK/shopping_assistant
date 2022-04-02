package com.byeduck.shoppingassistant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.byeduck.shoppingassistant.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}