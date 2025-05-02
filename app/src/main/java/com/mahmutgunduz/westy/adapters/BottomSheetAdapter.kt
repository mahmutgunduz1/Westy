package com.mahmutgunduz.westy.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mahmutgunduz.westy.Model.BottomShetModelSubn
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.databinding.BottomsheetRcvRecyclerRowBinding
import com.mahmutgunduz.westy.fragments.ProductFragment
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

        holder.binding.apply {
            categoryName.text = category.title

            
            // İkon ayarla
            try {
                // Önce drawable resource olarak dene
               val resourceId=     context.resources.getIdentifier("dasdssa", "drawable", context.packageName)

                if (resourceId != 0) {
                    categoryIcon.setImageResource(resourceId)
                } else {
                    // URL olarak dene
                    Glide.with(context)
                        .load(category.img)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error_image)
                        .into(categoryIcon)
                }
            } catch (e: Exception) {
                // Hata durumunda placeholder göster
                categoryIcon.setImageResource(R.drawable.placeholder_image)
            }

            // Tıklama işlemi
            root.setOnClickListener {
                // BottomSheet'i kapat
                bottomSheet.dismiss()

                // ProductFragment'a geçiş yap
                val activity = context as? AppCompatActivity
                activity?.let {
                    // ProductFragment'ı oluştur
                    val productFragment = ProductFragment.newInstance(category.title)
                    
                    // Fragment transaction'ı başlat
                    val fragmentTransaction = it.supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragmentContainerView, productFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            }
        }
    }
}