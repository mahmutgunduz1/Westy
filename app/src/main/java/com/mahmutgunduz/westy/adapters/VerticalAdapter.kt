package com.mahmutgunduz.westy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.westy.fragments.ProductFragment
import com.mahmutgunduz.westy.Model.VerticalModel
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.databinding.RecyclerRowVerticalBinding

class VerticalAdapter(
    private val list: List<VerticalModel>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<VerticalAdapter.RowHolder>() {

    class RowHolder(val binding: RecyclerRowVerticalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowVerticalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RowHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val item = list[position]
        
        holder.binding.apply {
            imageVieww.setImageResource(item.img)

        }

        holder.itemView.setOnClickListener {
            val productFragment = ProductFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, productFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}