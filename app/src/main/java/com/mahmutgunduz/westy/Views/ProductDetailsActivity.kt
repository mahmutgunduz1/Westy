package com.mahmutgunduz.westy.Views

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.mahmutgunduz.westy.Model.BottomShetModelSubn
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.data.model.Product
import com.mahmutgunduz.westy.dataBase.FavoritesDao
import com.mahmutgunduz.westy.dataBase.FavoritesData
import com.mahmutgunduz.westy.databinding.ActivityProductDetailsBinding
import com.mahmutgunduz.westy.viewmodel.CartViewModel
import com.mahmutgunduz.westy.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private val viewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private var selectedSize: String? = null // Seçili beden
    private var bottomModel: BottomShetModelSubn? = null
    private var dataProduct: Product? = null
    
    @Inject
    lateinit var favoritesDao: FavoritesDao
    
    private val compositeDisposable = CompositeDisposable()
    private var favoritesList = listOf<FavoritesData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load favorites to check status
        loadFavorites()

        // Intent'ten gelen veri türünü kontrol et ve işle
        if (intent.hasExtra("product")) {
            // BottomShetModelSubn tipinde veri var mı kontrol et
            val bottomShetModel = intent.getParcelableExtra<BottomShetModelSubn>("product")
            if (bottomShetModel != null) {
                bottomModel = bottomShetModel
                displayBottomModelDetails(bottomShetModel)
            } else {
                // Product tipinde veri var mı kontrol et
                val productModel = intent.getParcelableExtra<Product>("product")
                if (productModel != null) {
                    dataProduct = productModel
                    displayProductDetails(productModel)
                } else {
                    showError("Desteklenmeyen ürün tipi")
                }
            }
        } else {
            showError("Ürün bilgisi bulunamadı")
        }

        setupListeners()
        setupSizeSelection()
    }
    
    private fun setupSizeSelection() {
        // ChipGroup'tan tüm chip'leri al
        binding.sizeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != View.NO_ID) {
                // Seçilen chip'i bul
                val chip = findViewById<Chip>(checkedId)
                selectedSize = chip.text.toString()
                
                // Sepete ekle butonunu aktif et
                binding.addToCartButton.isEnabled = true
                
                // Kullanıcıya bilgi ver
                Toast.makeText(this, "${selectedSize} bedeni seçildi", Toast.LENGTH_SHORT).show()
            } else {
                selectedSize = null
                binding.addToCartButton.isEnabled = false
            }
        }
    }
    
    private fun loadFavorites() {
        compositeDisposable.add(
            favoritesDao.getAllFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ favorites ->
                    favoritesList = favorites
                    updateFavoriteButton()
                }, { error ->
                    Log.e("ProductDetailsActivity", "Error loading favorites", error)
                })
        )
    }

    private fun displayBottomModelDetails(product: BottomShetModelSubn) {
        with(binding) {
            // Ürün başlığı
            productTitle.text = product.title
            
            // Ürün fiyatı
            productPrice.text = String.format("%.2f ₺", product.price)
            
            // İndirimli fiyat gösterimi
            originalPrice.text = String.format("%.2f ₺", product.oldPrice)
            originalPrice.paintFlags = originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            
            // Ürün açıklaması
            productDescription.text = product.discountInfo
            
            // Ürün resmi
            Glide.with(this@ProductDetailsActivity)
                .load(product.img)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(productImage)
        }
    }

    private fun displayProductDetails(product: Product) {
        with(binding) {
            // Ürün başlığı
            productTitle.text = product.title
            
            // Ürün fiyatı
            productPrice.text = String.format("%.2f ₺", product.price)
            
            // İndirimli fiyat gösterimi - standart ürünlerde indirim olmayabilir
            originalPrice.visibility = View.GONE
            
            // Ürün açıklaması
            productDescription.text = product.description
            
            // Ürün resmi
            Glide.with(this@ProductDetailsActivity)
                .load(product.image)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(productImage)
        }
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.favoriteButton.setOnClickListener {
            if (bottomModel != null) {
                toggleFavorite(bottomModel!!)
            } else if (dataProduct != null) {
                toggleFavoriteForProduct(dataProduct!!)
            }
        }

        binding.addToCartButton.setOnClickListener {
            if (selectedSize == null) {
                Toast.makeText(this, "Lütfen bir beden seçin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            if (bottomModel != null) {
                addToCart(bottomModel!!)
            } else if (dataProduct != null) {
                addProductToCart(dataProduct!!)
            }
        }
    }

    private fun toggleFavorite(product: BottomShetModelSubn) {
        val isFavorite = favoritesList.any { it.productId == product.id }

        if (isFavorite) {
            // Favorilerden kaldır
            compositeDisposable.add(
                favoritesDao.deleteFavoriteById(product.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Toast.makeText(this, "Favorilerden kaldırıldı", Toast.LENGTH_SHORT).show()
                        loadFavorites() // Favorileri yeniden yükle
                    }, { throwable ->
                        Log.e("ProductDetailsActivity", "Error removing from favorites", throwable)
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
                favoritesDao.insertFavorite(favoriteData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Toast.makeText(this, "Favorilere eklendi", Toast.LENGTH_SHORT).show()
                        loadFavorites() // Favorileri yeniden yükle
                    }, { throwable ->
                        Log.e("ProductDetailsActivity", "Error adding to favorites", throwable)
                    })
            )
        }
    }
    
    private fun toggleFavoriteForProduct(product: Product) {
        val isFavorite = favoritesList.any { it.productId == product.id }

        if (isFavorite) {
            // Favorilerden kaldır
            compositeDisposable.add(
                favoritesDao.deleteFavoriteById(product.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Toast.makeText(this, "Favorilerden kaldırıldı", Toast.LENGTH_SHORT).show()
                        loadFavorites() // Favorileri yeniden yükle
                    }, { throwable ->
                        Log.e("ProductDetailsActivity", "Error removing from favorites", throwable)
                    })
            )
        } else {
            // Favorilere ekle
            val favoriteData = FavoritesData(
                productId = product.id,
                productName = product.title,
                productPrice = product.price,
                productImage = product.image,
                productDescription = product.description
            )

            compositeDisposable.add(
                favoritesDao.insertFavorite(favoriteData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Toast.makeText(this, "Favorilere eklendi", Toast.LENGTH_SHORT).show()
                        loadFavorites() // Favorileri yeniden yükle
                    }, { throwable ->
                        Log.e("ProductDetailsActivity", "Error adding to favorites", throwable)
                    })
            )
        }
    }

    private fun addToCart(product: BottomShetModelSubn) {
        // Create a temporary Product to add to cart with the correct image resource
        val tempProduct = Product(
            id = product.id,
            title = product.title,
            price = product.price,
            description = "${product.discountInfo} - Beden: $selectedSize",
            category = "",
            // Store the image resource ID directly as a string
            image = product.img.toString()
        )
        
        cartViewModel.addToCart(tempProduct)
        Toast.makeText(this, "${selectedSize} beden ürün sepete eklendi", Toast.LENGTH_SHORT).show()
        
        // Navigate to cart screen
        navigateToCart()
    }
    
    private fun addProductToCart(product: Product) {
        // Add size information to the product description
        val productWithSize = Product(
            id = product.id,
            title = product.title,
            price = product.price,
            description = "${product.description} - Beden: $selectedSize",
            category = product.category,
            image = product.image,
            rating = product.rating
        )
        
        cartViewModel.addToCart(productWithSize)
        Toast.makeText(this, "${selectedSize} beden ürün sepete eklendi", Toast.LENGTH_SHORT).show()
        
        // Navigate to cart screen
        navigateToCart()
    }

    private fun navigateToCart() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("navigate_to_cart", true)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun updateFavoriteButton() {
        // Favori durumuna göre buton görünümünü güncelle
        val productId = bottomModel?.id ?: dataProduct?.id ?: -1
        val isFavorite = favoritesList.any { it.productId == productId }
        
        binding.favoriteButton.setImageResource(
            if (isFavorite) R.drawable.fav2 else R.drawable.fav1
        )
    }

    private fun showError(message: String) {
        binding.errorLayout.visibility = View.VISIBLE
        binding.contentLayout.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
