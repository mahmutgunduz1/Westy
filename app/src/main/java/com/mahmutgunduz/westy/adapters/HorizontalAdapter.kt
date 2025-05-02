package com.mahmutgunduz.westy.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.westy.Model.BottomShetModelSubn
import com.mahmutgunduz.westy.Model.HorizontalModel
import com.mahmutgunduz.westy.Views.ProductDetailsActivity
import com.mahmutgunduz.westy.databinding.RecyclerRowHorizontalBinding

class HorizontalAdapter(
    private val horizontalList: List<HorizontalModel>,
    private val context: Context
) : RecyclerView.Adapter<HorizontalAdapter.RowHolder>() {
    
    class RowHolder(val binding: RecyclerRowHorizontalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowHorizontalBinding.inflate(
            LayoutInflater.from(parent.context), 
            parent, 
            false
        )
        return RowHolder(binding)
    }

    override fun getItemCount(): Int = horizontalList.size

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val item = horizontalList[position]
        
        with(holder.binding) {
            imgRow2.setImageResource(item.img)
            txtRow2.text = item.txt
            
            // Set click listener to navigate to product details
            root.setOnClickListener {
                navigateToProductDetails(item)
            }
        }
    }
    
    private fun navigateToProductDetails(item: HorizontalModel) {
        // Convert HorizontalModel to BottomShetModelSubn
        val product = BottomShetModelSubn(
            id = item.id,
            title = item.txt,
            img = item.img,
            price = item.price,
            oldPrice = item.oldPrice,
            newPrice = item.price,
            discountInfo = item.discountInfo
        )
        
        // Create intent to ProductDetailsActivity
        val intent = Intent(context, ProductDetailsActivity::class.java).apply {
            putExtra("product", product)
        }
        context.startActivity(intent)
    }
}