package com.byeduck.shoppingassistant.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.byeduck.shoppingassistant.databinding.ListelemBrandBinding

class BrandsListElementAdapter(val context: Context, val deleteCallback: (String) -> Unit) :
    ListAdapter<String, BrandsListElementAdapter.BrandListViewHolder>(BrandsDiffCallback()) {

    inner class BrandListViewHolder(val binding: ListelemBrandBinding) :
        RecyclerView.ViewHolder(binding.root)

    private class BrandsDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListelemBrandBinding.inflate(inflater, parent, false)
        return BrandListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrandListViewHolder, position: Int) {
        val current = getItem(position)
        holder.binding.brandName.text = current
        holder.binding.deleteBrandButton.setOnClickListener { deleteCallback(current) }
    }
}