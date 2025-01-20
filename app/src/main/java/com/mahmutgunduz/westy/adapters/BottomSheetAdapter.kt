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
        holder.binding.categoryIcon.setImageResource(category.img)
        
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