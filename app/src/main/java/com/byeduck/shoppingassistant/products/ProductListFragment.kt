package com.byeduck.shoppingassistant.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.byeduck.shoppingassistant.R
import com.byeduck.shoppingassistant.ResponseHandler
import com.byeduck.shoppingassistant.databinding.FragmentProductListBinding
import com.byeduck.shoppingassistant.db.SearchEntity
import com.byeduck.shoppingassistant.dialogs.ErrorDialog
import com.byeduck.shoppingassistant.dialogs.LoadingDialog
import com.byeduck.shoppingassistant.products.remote.Product
import com.byeduck.shoppingassistant.products.remote.ScrapAPI
import com.byeduck.shoppingassistant.remote.ErrorResponse
import com.byeduck.shoppingassistant.remote.RetrofitProvider
import com.byeduck.shoppingassistant.searches.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class ProductListFragment : Fragment() {

    private val responseHandler = ResponseHandler()
    private lateinit var scrapApiService: ScrapAPI
    private lateinit var binding: FragmentProductListBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var category: String
    private lateinit var query: String
    private lateinit var products: List<Product>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        binding.persistSearchButton.setOnClickListener {
            val entity = SearchEntity(products, query, category)
            lifecycleScope.launch {
                searchViewModel.addShoppingList(entity)
            }
        }
        searchViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[SearchViewModel::class.java]
        scrapApiService = RetrofitProvider.getRetrofit(getString(R.string.backend_base_url))
            .create(ScrapAPI::class.java)
        category =
            arguments?.getString("category") ?: throw IllegalStateException("Category not provided")
        query = arguments?.getString("query") ?: throw IllegalStateException("Query not provided")
        loadingDialog = LoadingDialog(layoutInflater, requireContext())
        loadingDialog.startLoading()
        lifecycleScope.launch(Dispatchers.IO) {
            val productsResponse = scrapApiService.getProducts(category, query).awaitResponse()
            withContext(Dispatchers.Main) {
                responseHandler.handleResponse(
                    productsResponse,
                    this@ProductListFragment::initialize,
                    this@ProductListFragment::handleError
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

    private fun initialize(products: List<Product>) {
        this.products = products
        val recyclerViewAdapter =
            ProductsListElementAdapter(requireContext(), products)
        binding.productsListRecycleView.adapter = recyclerViewAdapter
        binding.productsListRecycleView.addItemDecoration(
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        )
        loadingDialog.stopLoading()
    }
}