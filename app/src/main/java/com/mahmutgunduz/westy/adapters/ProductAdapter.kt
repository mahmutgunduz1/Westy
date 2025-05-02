package com.mahmutgunduz.westy.adapters

import android.content.Context
import android.content.Intent
import android.graphics.ColorSpace.Model
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmutgunduz.westy.Model.BottomShetModelSubn
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.Views.ProductDetailsActivity
import com.mahmutgunduz.westy.data.model.Product
import com.mahmutgunduz.westy.dataBase.FavoritesDao
import com.mahmutgunduz.westy.dataBase.FavoritesData
import com.mahmutgunduz.westy.databinding.ReyclerRowProductBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers



class ProductAdapter(
    private var productList: List<BottomShetModelSubn>,
    private val context: Context,
    private val dao: FavoritesDao
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val compositeDisposable = CompositeDisposable()
    private var favoritesList = listOf<FavoritesData>()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        compositeDisposable.add(
            dao.getAllFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ favorites: List<FavoritesData> ->
                    favoritesList = favorites
                    notifyDataSetChanged()
                }, { throwable: Throwable ->
                    Log.e("ProductAdapter", "Error loading favorites", throwable)
                })
        )
    }

    fun updateProducts(newProducts: List<BottomShetModelSubn>) {
        productList = newProducts
        notifyDataSetChanged()
    }

    class ProductViewHolder(val binding: ReyclerRowProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ReyclerRowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]

        with(holder.binding) {
            // Ürün bilgilerini göster
            textViewProductName.text = currentProduct.title
            textViewProductPrice.text = String.format("%.2f ₺", currentProduct.price)

            // Ürün resmini yükle
            Glide.with(context)
                .load(currentProduct.img)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageViewProduct)

            // Favorilerde olup olmadığını kontrol et
            val isFavorite = favoritesList.any { it.productId == currentProduct.id }
            imageViewFavorite.setImageResource(if (isFavorite) R.drawable.fav2 else R.drawable.fav1)

            // Favori butonuna tıklama
            imageViewFavorite.setOnClickListener {
                toggleFavorite( currentProduct)
            }

            // Ürüne tıklama
            root.setOnClickListener {
                navigateToProductDetails(currentProduct)
            }
        }
    }

    private fun toggleFavorite(product: BottomShetModelSubn) {
        val isFavorite = favoritesList.any { it.productId == product.id }

        if (isFavorite) {
            // Favorilerden kaldır
            compositeDisposable.add(
                dao.deleteFavoriteById(product.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Toast.makeText(context, "Favorilerden kaldırıldı", Toast.LENGTH_SHORT).show()
                        loadFavorites() // Favorileri yeniden yükle
                    }, { throwable ->
                        Log.e("ProductAdapter", "Error removing from favorites", throwable)
                    })
            )
        } else {
            // Favorilere ekle
            val favoriteData = FavoritesData(
                productId = product.id,
                productName = product.title,
                productPrice = product.price,
                productImage = product.img.toString(),
                productDescription = product.discountInfo
            )

            compositeDisposable.add(
                dao.insertFavorite(favoriteData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Toast.makeText(context, "Favorilere eklendi", Toast.LENGTH_SHORT).show()
                        loadFavorites() // Favorileri yeniden yükle
                    }, { throwable ->
                        Log.e("ProductAdapter", "Error adding to favorites", throwable)
                    })
            )
        }
    }

    private fun navigateToProductDetails(product: BottomShetModelSubn) {
        val intent = Intent(context, ProductDetailsActivity::class.java).apply {
            putExtra("product", product)
        }
        context.startActivity(intent)
    }

    fun dispose() {
        compositeDisposable.clear()
    }
}
