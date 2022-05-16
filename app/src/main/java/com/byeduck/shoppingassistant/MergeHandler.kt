package com.byeduck.shoppingassistant

import com.byeduck.shoppingassistant.db.Identifiable

abstract class MergeHandler<T : Identifiable> {

    fun handleMerge(base: List<T>, new: List<T>): ChangeList<T> {
        val baseIdToPositionMap: Map<Long, Int> =
            base.mapIndexed { index, entity -> entity.id to index }.toMap()
        val newIdToEntityMap: Map<Long, T> = new.associateBy { it.id }

        val toBeRemoved: MutableList<Int> = ArrayList()
        val toBeAdded: MutableList<T> = ArrayList()
        val toBeModified: MutableMap<Int, T> = HashMap()

        baseIdToPositionMap.forEach { (id, position) ->
            run {
                if (!newIdToEntityMap.containsKey(id)) {
                    toBeRemoved.add(position)
                }
            }
        }
        newIdToEntityMap.forEach { (id, entity) ->
            run {
                if (baseIdToPositionMap.containsKey(id)) {
                    toBeModified[baseIdToPositionMap[id]!!] = entity
                } else {
                    toBeAdded.add(entity)
                }
            }
        }
        return ChangeList(toBeModified, toBeAdded, toBeRemoved)
    }
}