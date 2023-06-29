package com.hasanaltunbay.kriptopara.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasanaltunbay.kriptopara.databinding.RowBinding
import com.hasanaltunbay.kriptopara.model.KriptoModel

class Adapter(private val kriptoList : ArrayList<KriptoModel>, private val listener : Listener) : RecyclerView.Adapter<Adapter.RowHolder>() {

    interface Listener {
        fun onItemClick(kriptoModel: KriptoModel)
    }


    class RowHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return kriptoList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(kriptoList.get(position))
        }

        holder.binding.textName.text = kriptoList.get(position).currency
        holder.binding.textPrice.text = kriptoList.get(position).price

    }


}