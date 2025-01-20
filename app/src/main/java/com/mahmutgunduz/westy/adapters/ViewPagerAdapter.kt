package com.mahmutgunduz.westy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.westy.databinding.ItemSliderImageBinding

class ViewPagerAdapter(private val images: List<Int>) : 
    RecyclerView.Adapter<ViewPagerAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(private val binding: ItemSliderImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(imageRes: Int) {
            binding.sliderImage.apply {
                setImageResource(imageRes)
                clipToOutline = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding = ItemSliderImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val actualPosition = position % images.size
        holder.bind(images[actualPosition])
    }

    override fun getItemCount(): Int = if (images.isEmpty()) 0 else images.size * 1000

    fun getRealPosition(position: Int): Int = position % images.size
} 