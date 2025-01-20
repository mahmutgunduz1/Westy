package com.mahmutgunduz.westy.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.mahmutgunduz.westy.Model.BottomSheetModelSubn
import com.mahmutgunduz.westy.Model.BottomShetModelSubn
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.Views.ProductDetailsActivity
import com.mahmutgunduz.westy.dataBase.FavoritesDao
import com.mahmutgunduz.westy.dataBase.FavoritesData
import com.mahmutgunduz.westy.dataBase.FavoritesDataBase
import com.mahmutgunduz.westy.databinding.ReyclerRowProductBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
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
                .subscribe({ favorites ->
                    favoritesList = favorites
                    notifyDataSetChanged()
                }, { throwable ->
                    Log.e("ProductAdapter", "Error loading favorites", throwable)
                })
        )
    }

    class ProductViewHolder(val binding: ReyclerRowProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflate = ReyclerRowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(inflate)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = productList[position]
        
        // Ürünün favorilerde olup olmadığını kontrol et
        val isFavorite = favoritesList.any { it.id == item.id }
        
        holder.binding.apply {
            // Ürün adı ve detayları bağlama
            productName.text = item.title
            
            // Favori ikonunu güncelle
            iconFavorite.setImageResource(
                if (isFavorite) R.drawable.fav2 else R.drawable.fav1
            )
            
            // Favori butonu tıklama
            iconFavorite.setOnClickListener {
                if (isFavorite) {
                    // Favorilerden kaldır
                    val favorite = favoritesList.find { it.id == item.id }
                    favorite?.let { favoriteToDelete -> 
                        compositeDisposable.add(
                            dao.deleteFavorite(favoriteToDelete)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        loadFavorites()
                                        Toast.makeText(context, "Favorilerden kaldırıldı", Toast.LENGTH_SHORT).show()
                                    }, { throwable ->
                                        Toast.makeText(context, "Hata: ${throwable.message}", Toast.LENGTH_SHORT).show()
                                    })
                        )
                    }
                } else {
                    // Favorilere ekle
                    val favorite = FavoritesData(
                        name = item.title,
                        price = item.price,
                        imageUrl = item.img.toString()
                    )
                    compositeDisposable.add(
                        dao.insert(favorite)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                    {
                                        loadFavorites()
                                        Toast.makeText(context, "Favorilere eklendi", Toast.LENGTH_SHORT).show()
                                    },
                                    { throwable ->
                                        Toast.makeText(context, "Hata: ${throwable.message}", Toast.LENGTH_SHORT).show()
                                    }
                                )
                    )
                }
            }

            // Ürün detay sayfasına yönlendirme
            cardProduct.setOnClickListener {
                val intent = Intent(context, ProductDetailsActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        compositeDisposable.clear()
    }
}
