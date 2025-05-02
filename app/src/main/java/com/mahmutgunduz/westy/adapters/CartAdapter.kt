package com.mahmutgunduz.westy.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.dataBase.CartData
import com.mahmutgunduz.westy.databinding.CartItemBinding
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(
    private var cartItems: List<CartData>,
    private val context: Context,
    private val onIncrement: (Int) -> Unit,
    private val onDecrement: (Int) -> Unit,
    private val onRemove: (Int) -> Unit,
    private val onItemClick: (CartData) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: CartItemBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: CartData) {
            with(binding) {
                // Set product details
                productName.text = item.productName
                val formattedPrice = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))
                    .format(item.productPrice)
                productPrice.text = formattedPrice
                
                // Set quantity
                quantityText.text = item.quantity.toString()
                
                // Set total price for this item
                val totalItemPrice = item.productPrice * item.quantity
                val formattedTotalPrice = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))
                    .format(totalItemPrice)
                productTotalPrice.text = formattedTotalPrice
                
                // Load product image with improved handling
                try {
                    // Try to parse image path as an integer (resource ID)
                    val imageResId = item.productImage.toIntOrNull()
                    if (imageResId != null) {
                        // If it's a valid resource ID, load it directly
                        Glide.with(context)
                            .load(imageResId)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(productImage)
                    } else {
                        // Otherwise, treat it as a URL or file path
                        Glide.with(context)
                            .load(item.productImage)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(productImage)
                    }
                } catch (e: Exception) {
                    Log.e("CartAdapter", "Error loading image: ${e.message}")
                    // If there's any error, show the error image
                    Glide.with(context)
                        .load(R.drawable.error_image)
                        .into(productImage)
                }
                
                // Set click listeners
                buttonIncrement.setOnClickListener {
                    onIncrement(item.productId)
                }
                
                buttonDecrement.setOnClickListener {
                    onDecrement(item.productId)
                }
                
                buttonRemove.setOnClickListener {
                    onRemove(item.productId)
                }
                
                // Set item click listener for navigating to product details
                root.setOnClickListener {
                    onItemClick(item)
                }
                
                // Also make the product image clickable
                productImage.setOnClickListener {
                    onItemClick(item)
                }
                
                // Make product name clickable
                productName.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }
    
    override fun getItemCount() = cartItems.size
    
    fun updateList(newList: List<CartData>) {
        cartItems = newList
        notifyDataSetChanged()
    }
} 