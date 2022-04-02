package com.byeduck.shoppingassistant.products

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.byeduck.shoppingassistant.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.productsListRecycleView.adapter = ProductsListElementAdapter()
        binding.productsListRecycleView.layoutManager = LinearLayoutManager(this)
    }
}