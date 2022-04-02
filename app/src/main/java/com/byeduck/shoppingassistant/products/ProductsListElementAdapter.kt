package com.byeduck.shoppingassistant.products

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.byeduck.shoppingassistant.databinding.ListelemProductBinding

class ProductsListElementAdapter :
    RecyclerView.Adapter<ProductsListElementAdapter.ProductsListElementViewHolder>() {

    inner class ProductsListElementViewHolder(val binding: ListelemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsListElementViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ProductsListElementViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}