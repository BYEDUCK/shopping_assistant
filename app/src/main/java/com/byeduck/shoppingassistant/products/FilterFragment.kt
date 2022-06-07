package com.byeduck.shoppingassistant.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.byeduck.shoppingassistant.R
import com.byeduck.shoppingassistant.ResponseHandler
import com.byeduck.shoppingassistant.databinding.FragmentFilterBinding
import com.byeduck.shoppingassistant.dialogs.ErrorDialog
import com.byeduck.shoppingassistant.dialogs.LoadingDialog
import com.byeduck.shoppingassistant.products.remote.ScrapAPI
import com.byeduck.shoppingassistant.remote.ErrorResponse
import com.byeduck.shoppingassistant.remote.RetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class FilterFragment : Fragment() {

    private val responseHandler = ResponseHandler()
    private lateinit var scrapAPI: ScrapAPI
    private lateinit var binding: FragmentFilterBinding
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        loadingDialog = LoadingDialog(layoutInflater, requireContext())
        scrapAPI = RetrofitProvider.getRetrofit(getString(R.string.backend_base_url))
            .create(ScrapAPI::class.java)
        loadingDialog.startLoading()
        lifecycleScope.launch(Dispatchers.IO) {
            val categoriesResponse = scrapAPI.getCategories().awaitResponse()
            withContext(Dispatchers.Main) {
                responseHandler.handleResponse(
                    categoriesResponse,
                    this@FilterFragment::initialize,
                    this@FilterFragment::handleError
                )
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }

    private fun handleError(errorResponse: ErrorResponse) {
        ErrorDialog(layoutInflater, requireContext(), errorResponse)
            .show()
        loadingDialog.dismiss()
    }

    private fun initialize(categories: List<String>) {
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            categories
        )
        binding.categoriesSpinner.adapter = spinnerAdapter
        binding.showProductsButton.setOnClickListener {
            val query = binding.queryTxt.text.toString()
            val category = categories[binding.categoriesSpinner.selectedItemPosition]
//            val intent = Intent(this, ProductListFragment::class.java).apply {
//                putExtra("category", category)
//                putExtra("query", query)
//            }
//            startActivity(intent)
        }
        loadingDialog.stopLoading()
    }
}