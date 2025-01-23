package com.mahmutgunduz.westy.Views

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.adapters.ProductImagesAdapter
import com.mahmutgunduz.westy.databinding.ActivityProductDetailsBinding

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private var selectedSize: String? = null // Seçili beden
    
    // ViewPager için görsel listesi
    private val images = listOf(
        R.drawable.photo10,
        R.drawable.photo11
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        // Üstü çizili fiyat gösterimi
        binding.originalPrice.paintFlags = binding.originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        
        setupProductImages()
        setupSizeChips()
    }

    private fun setupListeners() {
        // Tüm butonlar için click listener'ları ayarla
        with(binding) {
            backButton.setOnClickListener { onBackPressed() }
            shareButton.setOnClickListener { shareProduct() }
            favoriteButton.setOnClickListener { toggleFavorite() }
            addToCartButton.setOnClickListener { addToCart() }
        }
    }

    private fun setupProductImages() {
        // ViewPager adapter'ını ayarla
        binding.productImageViewPager.apply {
            adapter = ProductImagesAdapter(images)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL

            // Sayfa değişimlerini dinle
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    // İndikatörü güncelle
                    binding.imageIndicator.selectTab(binding.imageIndicator.getTabAt(position))
                }
            })
        }

        // TabLayout indikatörlerini ayarla
        repeat(images.size) {
            binding.imageIndicator.addTab(binding.imageIndicator.newTab())
        }
    }

    private fun setupSizeChips() {
        binding.sizeChipGroup.apply {
            // Varsayılan seçili beden yok
            clearCheck()
            
            // Chip'lere tıklanınca
            setOnCheckedChangeListener { group, checkedId ->
                val chip = group.findViewById<Chip>(checkedId)
                if (chip != null) {
                    selectedSize = chip.text.toString()
                    updateAddToCartButton()
                }
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

    override fun onDestroy() {
        super.onDestroy()
        // ViewPager callback'ini temizle
        binding.productImageViewPager.unregisterOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {}
        )
    }
}