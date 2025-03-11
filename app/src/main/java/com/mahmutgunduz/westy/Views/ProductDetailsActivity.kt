package com.mahmutgunduz.westy.Views

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.data.model.Product
import com.mahmutgunduz.westy.databinding.ActivityProductDetailsBinding
import com.mahmutgunduz.westy.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private val viewModel: ProductViewModel by viewModels()
    private var selectedSize: String? = null // Seçili beden
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent'ten ürün bilgisini al
        product = intent.getParcelableExtra<Product>("product")
        
        // Ürün bilgisi varsa doğrudan göster, yoksa API'den çek
        product?.let {
            displayProductDetails(it)
        } ?: run {
            // Ürün ID'sini al ve API'den çek
            val productId = intent.getIntExtra("productId", -1)
            if (productId != -1) {
                loadProductDetails(productId)
            } else {
                // Hata durumu
                showError("Ürün bilgisi bulunamadı")
            }
        }

        setupListeners()
    }

    private fun loadProductDetails(productId: Int) {
        // Yükleme durumunu göster
        binding.progressBar.visibility = View.VISIBLE
        
        // ViewModel'den ürün detaylarını çek
        viewModel.loadProductById(productId)
        
        // ViewModel'i gözlemle
        viewModel.product.observe(this, Observer { product ->
            binding.progressBar.visibility = View.GONE
            this.product = product
            displayProductDetails(product)
        })
        
        // Hata durumunu gözlemle
        viewModel.error.observe(this, Observer { error ->
            binding.progressBar.visibility = View.GONE
            error?.let {
                showError(it)
            }
        })
    }

    private fun displayProductDetails(product: Product) {
        with(binding) {
            // Ürün başlığı
            productTitle.text = product.title
            
            // Ürün fiyatı
            productPrice.text = String.format("%.2f ₺", product.price)
            
            // İndirimli fiyat gösterimi (örnek olarak %10 indirim)
            val calculatedOriginalPrice = product.price * 1.1
            originalPrice.text = String.format("%.2f ₺", calculatedOriginalPrice)
            originalPrice.paintFlags = originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            
            // Ürün açıklaması
            productDescription.text = product.description
            
            // Ürün kategorisi
            productCategory.text = product.category
            
            // Ürün resmi
            Glide.with(this@ProductDetailsActivity)
                .load(product.image)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(productImage)
            
            // Ürün değerlendirmesi
            product.rating?.let { rating ->
                ratingBar.rating = rating.rate.toFloat()
                ratingCount.text = "(${rating.count} değerlendirme)"
                ratingLayout.visibility = View.VISIBLE
            } ?: run {
                ratingLayout.visibility = View.GONE
            }
        }
    }

    private fun setupListeners() {
        // Tüm butonlar için click listener'ları ayarla
        with(binding) {
            backButton.setOnClickListener { finish() }
            shareButton.setOnClickListener { shareProduct() }
            favoriteButton.setOnClickListener { toggleFavorite() }
            addToCartButton.setOnClickListener { addToCart() }
            
            // Beden seçimi
            sizeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                val chip = group.findViewById<com.google.android.material.chip.Chip>(checkedIds.firstOrNull() ?: -1)
                selectedSize = chip?.text?.toString()
                updateAddToCartButton()
            }
        }
    }

    private fun updateAddToCartButton() {
        binding.addToCartButton.isEnabled = selectedSize != null
    }

    private fun shareProduct() {
        // Ürün paylaşma işlemi
        Toast.makeText(this, "Ürün paylaşılıyor...", Toast.LENGTH_SHORT).show()
    }

    private fun toggleFavorite() {
        val isFavorite = binding.favoriteButton.tag as? Boolean ?: false
        binding.favoriteButton.apply {
            tag = !isFavorite
            setImageResource(if (!isFavorite) R.drawable.fav2 else R.drawable.fav1)
        }
        // String resource kullan
        Toast.makeText(
            this,
            if (!isFavorite) getString(R.string.added_to_favorites) 
            else getString(R.string.removed_from_favorites),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun addToCart() {
        // Elvis operatörü ile null kontrolü
        selectedSize?.let {
            // Sepete ekleme işlemi
            Toast.makeText(this, getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(this, getString(R.string.select_size), Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        binding.errorLayout.visibility = View.VISIBLE
        binding.contentLayout.visibility = View.GONE
    }
}