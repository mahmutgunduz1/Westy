package com.mahmutgunduz.westy.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmutgunduz.westy.Model.BottomShetModelSubn
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.Views.ProductDetailsActivity
import com.mahmutgunduz.westy.databinding.FavoritesRecyclerRowBinding
import com.mahmutgunduz.westy.dataBase.FavoritesData
import com.mahmutgunduz.westy.dataBase.FavoritesDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import com.mahmutgunduz.westy.data.model.Product
import com.mahmutgunduz.westy.data.model.Rating

class FavoritesAdapter(
    private var favoritesList: ArrayList<FavoritesData>,
    private val context: Context,
    private val dao: FavoritesDao
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private val compositeDisposable = CompositeDisposable()

    inner class FavoritesViewHolder(private val binding: FavoritesRecyclerRowBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: FavoritesData) {
            with(binding) {
                productName.text = item.productName
                productPrice.text = "${item.productPrice} TL"

                // Ürün resmini ayarla
                Glide.with(context)
                    .load(item.productImage)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(productImage)

                root.setOnClickListener {
                    val product = Product(
                        id = item.productId,
                        title = item.productName,
                        price = item.productPrice,
                        description = item.productDescription,
                        category = "",  // You might want to add a category field to FavoritesData
                        image = item.productImage,
                        rating = null  // You might want to add a rating field to FavoritesData
                    )
                    val intent = Intent(context, ProductDetailsActivity::class.java)
                    intent.putExtra("product", product)
                    context.startActivity(intent)
                }

                buttonRemoveFavorite.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        removeFromFavorites(item, position)
                    }
                }
            }
        }
    }

    private fun removeFromFavorites(item: FavoritesData, position: Int) {
        dao.deleteFavoriteById(item.productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    favoritesList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, favoritesList.size)
                    Toast.makeText(context, "Favorilerden kaldırıldı", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(context, "Hata: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = FavoritesRecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        if (position < favoritesList.size) {
            holder.bind(favoritesList[position])
        }
    }

    override fun getItemCount() = favoritesList.size

    fun updateList(newList: List<FavoritesData>) {
        favoritesList.clear()
        favoritesList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        compositeDisposable.clear()
    }
}