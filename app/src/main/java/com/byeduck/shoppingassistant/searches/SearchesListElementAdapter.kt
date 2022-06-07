package com.byeduck.shoppingassistant.searches

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.byeduck.shoppingassistant.R
import com.byeduck.shoppingassistant.databinding.ListelemSearchBinding
import com.byeduck.shoppingassistant.db.SearchEntity
import java.time.format.DateTimeFormatter

class SearchesListElementAdapter(
    val context: Context
) : ListAdapter<SearchEntity, SearchesListElementAdapter.SearchesListViewHolder>(
    SearchesDiffCallback()
) {

    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    inner class SearchesListViewHolder(val binding: ListelemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition() = bindingAdapterPosition
                override fun getSelectionKey() = itemId
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListelemSearchBinding.inflate(inflater, parent, false)
        return SearchesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchesListViewHolder, position: Int) {
        getItem(position).let { current ->
            run {
                val selected = tracker?.isSelected(position.toLong()) ?: false
                if (selected) {
                    holder.binding.root.isSelected = true
                }
//                holder.binding.root.setOnClickListener {
//                    val dialog = SearchActionsDialog()
//                    dialog.show(fragmentManager, "search_actions")
//                }
                holder.binding.searchIdLabel.text = current.id.toString()
                holder.binding.searchDateLabel.text = current.date.format(
                    DateTimeFormatter.ofPattern(context.getString(R.string.date_fromat))
                )
                holder.binding.searchQueryLabel.text = selected.toString()
                holder.binding.searchCategoryLabel.text = current.category
            }
        }
    }
}

private class SearchesDiffCallback : DiffUtil.ItemCallback<SearchEntity>() {

    override fun areItemsTheSame(oldItem: SearchEntity, newItem: SearchEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchEntity, newItem: SearchEntity): Boolean {
        return oldItem == newItem
    }

}

class SearchesDetailsLookup(
    private val recyclerView: RecyclerView
) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as SearchesListElementAdapter.SearchesListViewHolder)
                .getItemDetails()
        }
        return null
    }

}