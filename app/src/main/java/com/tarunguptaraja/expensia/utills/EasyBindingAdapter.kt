package com.tarunguptaraja.expensia.utills

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tarunguptaraja.expensia.extensions.runOnMain
import kotlin.concurrent.thread

abstract class EasyBindingAdapter<T : Any, VB : androidx.viewbinding.ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val diffCallback: DiffUtil.ItemCallback<T>? = null,
    private val onUpdated: ((Boolean) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var currentList = listOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = bindingInflater(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    fun getItemAt(position: Int) = currentList[position]
    fun getItem(position: Int) = currentList[position]

    fun onItemRangeMoved(callback: () -> Unit) {
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                callback()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                callback()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                callback()
            }
        })
    }

    override fun getItemCount(): Int = currentList.size

    class Holder<VB : androidx.viewbinding.ViewBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)

    abstract fun onBindViewHolder(holder: Holder<VB>, position: Int)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            onBindViewHolder(holder as Holder<VB>, position)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    open fun submitList(newList: List<T>?) {
        if (newList != null) {
            if (diffCallback == null || currentList.isEmpty()) {
                currentList = newList
                notifyDataSetChanged()
            } else {
                thread {
                    val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                        override fun areItemsTheSame(
                            oldItemPosition: Int, newItemPosition: Int
                        ): Boolean = try {
                            diffCallback.areItemsTheSame(
                                currentList[oldItemPosition], newList[newItemPosition]
                            )
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            false
                        }

                        override fun areContentsTheSame(
                            oldItemPosition: Int, newItemPosition: Int
                        ): Boolean = try {
                            diffCallback.areContentsTheSame(
                                currentList[oldItemPosition], newList[newItemPosition]
                            )
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            false
                        }

                        override fun getOldListSize() = currentList.size
                        override fun getNewListSize() = newList.size
                    })

                    runOnMain {
                        result.dispatchUpdatesTo(this)
                        if (onUpdated != null) {
                            val haveLengthChanges = if (currentList == newList) {
                                currentList.indices.any { currentList[it] != newList[it] }
                            } else {
                                true
                            }
                            onUpdated.invoke(haveLengthChanges)
                        }
                        currentList = newList
                    }
                }
            }
        }
    }

    fun addList(list: List<T>) {
        val newList = ArrayList(currentList)
        newList.addAll(list)
        submitList(newList)
    }

    fun onCurrentListChanged(previousList: List<T>, currentList: List<T>) {}
}
