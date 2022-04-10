package com.byeduck.shoppingassistant.products

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.byeduck.shoppingassistant.LoadingDialog
import com.byeduck.shoppingassistant.R
import com.byeduck.shoppingassistant.databinding.ActivityFilterBinding
import com.byeduck.shoppingassistant.products.remote.RetrofitProvider
import com.byeduck.shoppingassistant.products.remote.ScrapAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

class FilterActivity : AppCompatActivity() {

    private lateinit var scrapAPI: ScrapAPI
    private lateinit var binding: ActivityFilterBinding
    private lateinit var categories: List<String>
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(this)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scrapAPI = RetrofitProvider.getRetrofit(getString(R.string.backend_base_url))
            .create(ScrapAPI::class.java)
        loadingDialog.startLoading()
        lifecycleScope.launch(Dispatchers.IO) {
            categories = scrapAPI.getCategories().await()
            withContext(Dispatchers.Main) {
                initialize(categories)
            }
        }
        binding.showProductsButton.setOnClickListener {
            val query = binding.queryTxt.text.toString()
            val category = categories[binding.categoriesSpinner.selectedItemPosition]
            val intent = Intent(this, ProductListActivity::class.java).apply {
                putExtra("category", category)
                putExtra("query", query)
            }
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }

    private fun initialize(categories: List<String>) {
        val spinnerAdapter = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            categories
        )
        binding.categoriesSpinner.adapter = spinnerAdapter
        loadingDialog.stopLoading()
    }
}