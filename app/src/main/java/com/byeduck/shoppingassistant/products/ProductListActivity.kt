package com.byeduck.shoppingassistant.products

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.byeduck.shoppingassistant.LoadingDialog
import com.byeduck.shoppingassistant.R
import com.byeduck.shoppingassistant.databinding.ActivityProductListBinding
import com.byeduck.shoppingassistant.products.remote.Product
import com.byeduck.shoppingassistant.products.remote.RetrofitProvider
import com.byeduck.shoppingassistant.products.remote.ScrapAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

class ProductListActivity : AppCompatActivity() {

    private lateinit var scrapApiService: ScrapAPI
    private lateinit var binding: ActivityProductListBinding
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scrapApiService = RetrofitProvider.getRetrofit(getString(R.string.backend_base_url))
            .create(ScrapAPI::class.java)
        val extras = intent.extras ?: throw IllegalStateException("Extras are required")
        val category =
            extras.getString("category") ?: throw IllegalStateException("Category not provided")
        val query = extras.getString("query") ?: throw IllegalStateException("Query not provided")
        loadingDialog = LoadingDialog(this)
        loadingDialog.startLoading()
        lifecycleScope.launch(Dispatchers.IO) {
            val products = scrapApiService.getProducts(category, query).await()
            withContext(Dispatchers.Main) {
                initialize(products)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }

    private fun initialize(products: List<Product>) {
        val recyclerViewAdapter =
            ProductsListElementAdapter(applicationContext, products)
        binding.productsListRecycleView.adapter = recyclerViewAdapter
        binding.productsListRecycleView.layoutManager = LinearLayoutManager(applicationContext)
        loadingDialog.stopLoading()
    }
}