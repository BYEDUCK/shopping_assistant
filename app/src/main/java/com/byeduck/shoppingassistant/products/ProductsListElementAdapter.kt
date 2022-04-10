package com.byeduck.shoppingassistant.products

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.byeduck.shoppingassistant.R
import com.byeduck.shoppingassistant.databinding.ListelemProductBinding
import com.byeduck.shoppingassistant.products.remote.Product

class ProductsListElementAdapter(
    private val context: Context,
    private val products: List<Product>
) : RecyclerView.Adapter<ProductsListElementAdapter.ProductsListElementViewHolder>() {

    inner class ProductsListElementViewHolder(val binding: ListelemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsListElementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListelemProductBinding.inflate(inflater, parent, false)
        return ProductsListElementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsListElementViewHolder, position: Int) {
        val current = products[position]
        holder.binding.productNameLabel.text = current.name
        holder.binding.productPriceLabel.text = current.price.toPlainString()
        holder.binding.productScoreLabel.text = context.getString(
            R.string.product_score, current.score.score, current.score.maxScore
        )
    }

    override fun getItemCount(): Int = products.size
}