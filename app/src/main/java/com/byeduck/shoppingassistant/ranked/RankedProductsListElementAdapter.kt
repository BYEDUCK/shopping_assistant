package com.byeduck.shoppingassistant.ranked

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.byeduck.shoppingassistant.databinding.ListelemRankedProductBinding

class RankedProductsListElementAdapter(
    private val activity: Activity,
    private val rankedProducts: List<RankedProduct>
) : RecyclerView.Adapter<RankedProductsListElementAdapter.RankedProductsListElementViewHolder>() {

    inner class RankedProductsListElementViewHolder(val binding: ListelemRankedProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RankedProductsListElementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListelemRankedProductBinding.inflate(inflater, parent, false)
        return RankedProductsListElementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankedProductsListElementViewHolder, position: Int) {
        val current = rankedProducts[position]
        holder.binding.productName.text = current.name
        holder.binding.productRank.text = current.rank.toPlainString()
        holder.binding.showButton.setOnClickListener {
            if (current.url == null) {
                Toast.makeText(activity, "URL not available", Toast.LENGTH_SHORT).show()
            } else {
                val uri = Uri.parse(current.url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                activity.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = rankedProducts.size
}