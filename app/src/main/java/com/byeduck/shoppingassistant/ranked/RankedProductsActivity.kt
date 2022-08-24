package com.byeduck.shoppingassistant.ranked

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.byeduck.shoppingassistant.*
import com.byeduck.shoppingassistant.databinding.ActivityRankedProductsBinding
import com.byeduck.shoppingassistant.db.SearchEntity
import com.byeduck.shoppingassistant.dialogs.LoadingDialog
import com.byeduck.shoppingassistant.functions.GoogleCloudFunctionsAPI
import com.byeduck.shoppingassistant.remote.RetrofitProvider
import com.byeduck.shoppingassistant.searches.SearchListActivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class RankedProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRankedProductsBinding
    private lateinit var googleCloudFunctionsAPI: GoogleCloudFunctionsAPI
    private lateinit var loadingDialog: LoadingDialog
    private val responseHandler = ResponseHandler()
    private var showProductsCount: Int = TOP_PRODUCTS_COUNT_DEFAULT_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(this)
        loadingDialog.startLoading()
        binding = ActivityRankedProductsBinding.inflate(layoutInflater)
        googleCloudFunctionsAPI =
            RetrofitProvider.getRetrofit(getString(R.string.google_cloud_functions_url))
                .create(GoogleCloudFunctionsAPI::class.java)
        val extras = intent.extras ?: throw IllegalStateException("Extras required")
        val serializedSearch =
            extras.getString("search") ?: throw IllegalStateException("Search required")
        val search = Gson().fromJson(serializedSearch, SearchEntity::class.java)
        val sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
        val appConfig = getAppConfig(sharedPreferences)
        showProductsCount = appConfig.topProductsCount
        val request = RankRequest(search.products, appConfig)
        lifecycleScope.launch(Dispatchers.IO) {
            val response = googleCloudFunctionsAPI.rankProducts(request).awaitResponse()
            withContext(Dispatchers.Main) {
                responseHandler.handleResponse(
                    response,
                    this@RankedProductsActivity::initializeRecyclerView,
                    this@RankedProductsActivity::handleError
                )
            }
        }
        setContentView(binding.root)
    }

    private fun initializeRecyclerView(rankedProducts: List<RankedProduct>) {
        val adapter = RankedProductsListElementAdapter(
            this, rankedProducts.sortedByDescending { it.rank }.take(showProductsCount)
        )
        binding.rankedProductsView.layoutManager = LinearLayoutManager(this)
        binding.rankedProductsView.adapter = adapter
        binding.rankedProductsView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        loadingDialog.stopLoading()
    }

    private fun handleError(errorResponse: GenericResponse) {
        Toast.makeText(
            this,
            "Error(${errorResponse.status}. Try again later",
            Toast.LENGTH_LONG
        ).show()
        val goToListIntent = Intent(this, SearchListActivity::class.java)
        startActivity(goToListIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }
}