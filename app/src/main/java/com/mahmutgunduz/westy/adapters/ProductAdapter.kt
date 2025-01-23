package com.mahmutgunduz.westy.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.mahmutgunduz.westy.Model.BottomShetModelSubn
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.Views.ProductDetailsActivity

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
            oldPrice.text = item.oldPrice.toString()
            newPrice.text = item.newPrice.toString()
            discountInfo.text = item.discountInfo

            // Ürün resmini ayarla
            try {
                imageProduct.setImageResource(item.img)
            } catch (e: Exception) {
                Log.e("ProductAdapter", "Error loading image: ${e.message}")
                imageProduct.setImageResource(R.drawable.categories1)
            }

            // Favori ikonunu güncelle (fav1 veya fav2)
            iconFavorite.setImageResource(
                if (isFavorite) R.drawable.fav2 else R.drawable.fav1
            )

            // Add click listener to the entire item
            root.setOnClickListener {
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra("product", item)
                context.startActivity(intent)
            }

            iconFavorite.setOnClickListener {
                toggleFavorite(item, holder)
            }
        }
    }

    private fun toggleFavorite(item: BottomShetModelSubn, holder: ProductViewHolder) {
        compositeDisposable.add(
            dao.getAllFavorites() // Önce tüm favorileri al
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ favorites ->
                    val isFavorite = favorites.any { it.id == item.id }
                    if (isFavorite) {
                        removeFromFavorites(item, holder)
                    } else {
                        addToFavorites(item, holder)
                    }
                }, { error ->
                    Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show()
                })
        )
    }

    private fun addToFavorites(item: BottomShetModelSubn, holder: ProductViewHolder) {
        val favorite = item.toFavoritesData()
        compositeDisposable.add(
            dao.insert(favorite)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loadFavorites()
                    Toast.makeText(context, context.getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show()
                    holder.binding.iconFavorite.setImageResource(R.drawable.fav2)
                }, { throwable ->
                    Toast.makeText(context, "Hata: ${throwable.message}", Toast.LENGTH_SHORT).show()
                })
        )
    }

    private fun removeFromFavorites(item: BottomShetModelSubn, holder: ProductViewHolder) {
        val favorite = favoritesList.find { it.id == item.id }
        favorite?.let { favoriteToDelete ->
            compositeDisposable.add(
                dao.deleteFavorite(favoriteToDelete)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        loadFavorites()
                        Toast.makeText(context, context.getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT).show()
                        holder.binding.iconFavorite.setImageResource(R.drawable.fav1)
                    }, { throwable ->
                        Toast.makeText(context, "Hata: ${throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
                    })
            )
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        compositeDisposable.clear()
    }

    fun BottomShetModelSubn.toFavoritesData(): FavoritesData {
        return FavoritesData(
            id = this.id,
            name = this.title,
            price = this.newPrice,
            imageUrl = this.img  // This is the drawable resource ID (e.g., R.drawable.photo9)
        )
    }

    fun FavoritesData.toBottomShetModelSubn(): BottomShetModelSubn {
        return BottomShetModelSubn(
            id = this.id,
            title = this.name,
            price = this.price,
            img = this.imageUrl.toInt(),
            oldPrice = 0.1,
            newPrice = 0.1,
            discountInfo = ""
        )
    }

}
