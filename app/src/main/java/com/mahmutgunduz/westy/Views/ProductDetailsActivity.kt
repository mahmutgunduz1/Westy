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
    private var selectedSize: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        // Üstü çizili fiyat
        binding.originalPrice.paintFlags = binding.originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        // ViewPager için görseller
        setupProductImages()

        // Beden seçimi için chip'ler
        setupSizeChips()
    }

    private fun setupListeners() {
        // Geri butonu
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        // Paylaş butonu
        binding.shareButton.setOnClickListener {
            shareProduct()
        }

        // Favori butonu
        binding.favoriteButton.setOnClickListener {
            toggleFavorite()
        }

        // Sepete ekle butonu
        binding.addToCartButton.setOnClickListener {
            addToCart()
        }

        // Beden seçimi
        binding.sizeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            selectedSize = chip?.text?.toString()
        }
    }

    private fun setupProductImages() {
        val images = listOf(
            R.drawable.photo10,
            R.drawable.photo11
        )

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
        // Favori durumunu değiştir
        val isFavorite = binding.favoriteButton.tag as? Boolean ?: false
        binding.favoriteButton.apply {
            tag = !isFavorite
            setImageResource(if (!isFavorite) R.drawable.fav2 else R.drawable.fav1)
        }
        Toast.makeText(
            this,
            if (!isFavorite) "Favorilere eklendi" else "Favorilerden çıkarıldı",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun addToCart() {
        if (selectedSize == null) {
            Toast.makeText(this, "Lütfen bir beden seçin", Toast.LENGTH_SHORT).show()
            return
        }

        // Sepete ekleme işlemi
        Toast.makeText(this, "Ürün sepete eklendi", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // ViewPager callback'ini temizle
        binding.productImageViewPager.unregisterOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {}
        )
    }
}