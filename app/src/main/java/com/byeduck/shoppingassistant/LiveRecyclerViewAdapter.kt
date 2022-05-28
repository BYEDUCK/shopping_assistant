package com.byeduck.shoppingassistant

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.byeduck.shoppingassistant.db.Identifiable

abstract class LiveRecyclerViewAdapter<T : Identifiable, VH : RecyclerView.ViewHolder>(
    private val mergeHandler: MergeHandler<T>,
    liveData: LiveData<List<T>>,
    lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<VH>() {

    private var entities: MutableList<T> = ArrayList()

    init {
        liveData.observe(lifecycleOwner) { items ->
            val changeList = mergeHandler.handleMerge(entities, items)
            changeList.toBeModified.forEach { (position, modification) ->
                run {
                    entities[position] = modification
                    notifyItemChanged(position)
                }
            }
            changeList.toBeAdded.forEach {
                entities.add(it)
                notifyItemInserted(entities.size - 1)
            }
            changeList.toBeRemoved.forEach {
                entities.removeAt(it)
                notifyItemRemoved(it)
            }
        }
    }

    abstract fun doCreateViewHolder(parent: ViewGroup, viewType: Int): VH
    abstract fun doBindViewHolder(holder: VH, position: Int, current: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        doCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: VH, position: Int) =
        doBindViewHolder(holder, position, entities[position])

    override fun getItemCount(): Int = entities.size
}