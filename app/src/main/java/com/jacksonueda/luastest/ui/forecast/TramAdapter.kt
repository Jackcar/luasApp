package com.jacksonueda.luastest.ui.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jacksonueda.luastest.databinding.ItemTramBinding
import com.jacksonueda.luastest.model.Tram

class TramAdapter : RecyclerView.Adapter<TramAdapter.ViewHolder>() {

    private val items: MutableList<Tram> = mutableListOf()

    fun setItems(tramsList: List<Tram>) {
        items.clear()
        items.addAll(tramsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTramBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tram = items[position]
            executePendingBindings()
        }
    }

    class ViewHolder(val binding: ItemTramBinding): RecyclerView.ViewHolder(binding.root)

}