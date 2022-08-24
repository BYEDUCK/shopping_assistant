package com.byeduck.shoppingassistant.products

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.byeduck.shoppingassistant.GenericResponse
import com.byeduck.shoppingassistant.MainActivity
import com.byeduck.shoppingassistant.R
import com.byeduck.shoppingassistant.ResponseHandler
import com.byeduck.shoppingassistant.databinding.ActivityProductListBinding
import com.byeduck.shoppingassistant.db.SearchEntity
import com.byeduck.shoppingassistant.dialogs.ErrorDialog
import com.byeduck.shoppingassistant.dialogs.LoadingDialog
import com.byeduck.shoppingassistant.products.remote.Product
import com.byeduck.shoppingassistant.products.remote.ScrapAPI
import com.byeduck.shoppingassistant.remote.ErrorResponse
import com.byeduck.shoppingassistant.remote.RetrofitProvider
import com.byeduck.shoppingassistant.searches.SearchListActivity
import com.byeduck.shoppingassistant.searches.SearchViewModel
import com.google.gson.Gson
import io.vavr.control.Try
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class ProductListActivity : AppCompatActivity() {

    private val responseHandler = ResponseHandler()
    private lateinit var scrapApiService: ScrapAPI
    private lateinit var binding: ActivityProductListBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var category: String
    private lateinit var query: String
    private lateinit var products: List<Product>
    private var minPrice: Int? = null
    private var maxPrice: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        binding.persistSearchButton.setOnClickListener {
            val entity = SearchEntity(products, query, category, minPrice, maxPrice)
            lifecycleScope.launch {
                val id = searchViewModel.addShoppingList(entity)
                Toast.makeText(
                    this@ProductListActivity,
                    "Created search with id $id",
                    Toast.LENGTH_LONG
                )
                    .show()
                val intent = Intent(this@ProductListActivity, SearchListActivity::class.java)
                    .apply { putExtra("searchId", id) }
                startActivity(intent)
            }
        }
        searchViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[SearchViewModel::class.java]
        setContentView(binding.root)
        scrapApiService = RetrofitProvider.getRetrofit(getString(R.string.backend_base_url))
            .create(ScrapAPI::class.java)
        val extras = intent.extras ?: throw IllegalStateException("Extras are required")
        category =
            extras.getString("category") ?: throw IllegalStateException("Category not provided")
        query = extras.getString("query") ?: throw IllegalStateException("Query not provided")
        minPrice = extras.get("minPrice") as Int?
        maxPrice = extras.get("maxPrice") as Int?
        loadingDialog = LoadingDialog(this)
        loadingDialog.startLoading()
        lifecycleScope.launch(Dispatchers.IO) {
            val productsResponse =
                scrapApiService.getProducts(category, query, minPrice, maxPrice).awaitResponse()
            withContext(Dispatchers.Main) {
                responseHandler.handleResponse(
                    productsResponse,
                    this@ProductListActivity::initialize,
                    this@ProductListActivity::handleError
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun handleError(errorResponse: GenericResponse) {
        val parsed = Try.of { Gson().fromJson(errorResponse.body, ErrorResponse::class.java) }
            .getOrElse(ErrorResponse.getDefault(errorResponse.status))
        ErrorDialog(this, parsed)
            .show(this::goToMain)
        loadingDialog.dismiss()
    }

    private fun initialize(products: List<Product>) {
        this.products = products
        val recyclerViewAdapter =
            ProductsListElementAdapter(applicationContext, products)
        binding.productsListRecycleView.adapter = recyclerViewAdapter
        binding.productsListRecycleView.layoutManager = LinearLayoutManager(applicationContext)
        binding.productsListRecycleView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        loadingDialog.stopLoading()
    }
}