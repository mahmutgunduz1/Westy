package com.mahmutgunduz.westy.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mahmutgunduz.westy.fragments.ProductFragment
import com.mahmutgunduz.westy.Model.BottomShetModelSubn
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.databinding.BottomsheetRcvRecyclerRowBinding
import com.bumptech.glide.Glide

class BottomSheetAdapter(
    private val bottomSheetModel: List<BottomShetModelSubn>,
    private val context: Context,
    private val bottomSheet: BottomSheetDialog
) : RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {

    class ViewHolder(val binding: BottomsheetRcvRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BottomsheetRcvRecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return bottomSheetModel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = bottomSheetModel[position]

        holder.binding.categoryName.text = category.title
        
        // Handle string image resource
        try {
            // Try to parse as a resource ID
            val resourceId = context.resources.getIdentifier(
                category.img.replace("R.drawable.", ""),
                "drawable",
                context.packageName
            )
            if (resourceId != 0) {
                holder.binding.categoryIcon.setImageResource(resourceId)
            } else {
                // If not a resource ID, try to load as a URL
                Glide.with(context)
                    .load(category.img)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(holder.binding.categoryIcon)
            }
        } catch (e: Exception) {
            // If parsing fails, try to load as a URL
            Glide.with(context)
                .load(category.img)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.binding.categoryIcon)
        }
        
        holder.binding.card.setOnClickListener {
            bottomSheet.dismiss()
            Toast.makeText(context, category.title, Toast.LENGTH_SHORT).show()

            // Fragment'a veri gönderme
            val bundle = Bundle().apply {
                putInt("selectedCategoryId", category.id)
            }

            // ProductFragment ile geçiş
            val secondFragment = ProductFragment().apply {
                arguments = bundle
            }

            val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainerView, secondFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}