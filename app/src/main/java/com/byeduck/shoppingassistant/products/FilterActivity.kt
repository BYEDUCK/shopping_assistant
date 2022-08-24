package com.byeduck.shoppingassistant.products

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.byeduck.shoppingassistant.GenericResponse
import com.byeduck.shoppingassistant.MainActivity
import com.byeduck.shoppingassistant.R
import com.byeduck.shoppingassistant.ResponseHandler
import com.byeduck.shoppingassistant.databinding.ActivityFilterBinding
import com.byeduck.shoppingassistant.dialogs.ErrorDialog
import com.byeduck.shoppingassistant.dialogs.LoadingDialog
import com.byeduck.shoppingassistant.products.remote.ScrapAPI
import com.byeduck.shoppingassistant.remote.ErrorResponse
import com.byeduck.shoppingassistant.remote.RetrofitProvider
import com.google.gson.Gson
import io.vavr.control.Try
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class FilterActivity : AppCompatActivity() {

    private val responseHandler = ResponseHandler()
    private lateinit var scrapAPI: ScrapAPI
    private lateinit var binding: ActivityFilterBinding
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
            val categoriesResponse = scrapAPI.getCategories().awaitResponse()
            withContext(Dispatchers.Main) {
                responseHandler.handleResponse(
                    categoriesResponse,
                    this@FilterActivity::initialize,
                    this@FilterActivity::handleError
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }

    private fun handleError(errorResponse: GenericResponse) {
        val parsed = Try.of { Gson().fromJson(errorResponse.body, ErrorResponse::class.java) }
            .getOrElse(ErrorResponse.getDefault(errorResponse.status))
        ErrorDialog(this, parsed)
            .show(this::goToMain)
        loadingDialog.dismiss()
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun initialize(categories: List<String>) {
        val spinnerAdapter = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            categories
        )
        binding.categoriesSpinner.adapter = spinnerAdapter
        binding.showProductsButton.setOnClickListener {
            val query = binding.queryTxt.text.toString()
            val category = categories[binding.categoriesSpinner.selectedItemPosition]
            val minPrice = binding.minPrice.text.toString().toIntOrNull()
            val maxPrice = binding.maxPrice.text.toString().toIntOrNull()
            val intent = Intent(this, ProductListActivity::class.java).apply {
                putExtra("category", category)
                putExtra("query", query)
                putExtra("minPrice", minPrice)
                putExtra("maxPrice", maxPrice)
            }
            startActivity(intent)
        }
        loadingDialog.stopLoading()
    }
}