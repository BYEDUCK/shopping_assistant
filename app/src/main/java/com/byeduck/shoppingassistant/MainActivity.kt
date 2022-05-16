package com.byeduck.shoppingassistant

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.byeduck.shoppingassistant.databinding.ActivityMainBinding
import com.byeduck.shoppingassistant.products.FilterActivity
import com.byeduck.shoppingassistant.searches.SearchListActivity
import com.byeduck.shoppingassistant.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.enterFilterButton.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
        }
        binding.enterSettingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        binding.enterSearchesButton.setOnClickListener {
            val intent = Intent(this, SearchListActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }
}