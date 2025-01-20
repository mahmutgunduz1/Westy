package com.mahmutgunduz.westy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.westy.Model.HorizontalModel

import com.mahmutgunduz.westy.databinding.RecyclerRowHorizontalBinding

class HorizontalAdapter(val horizontalList: List<HorizontalModel>): RecyclerView.Adapter<HorizontalAdapter.RowHolder>() {
    private lateinit var layoutInflater: LayoutInflater
    class RowHolder(val binding: RecyclerRowHorizontalBinding):RecyclerView.ViewHolder(binding.root){



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            RecyclerRowHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
      return horizontalList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val item = horizontalList[position]
        holder.binding.imgRow2.setImageResource(item.img)
        holder.binding.txtRow2.text=item.txt

    }
}