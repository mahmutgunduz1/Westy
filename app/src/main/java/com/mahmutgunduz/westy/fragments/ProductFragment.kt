package com.mahmutgunduz.westy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmutgunduz.westy.adapters.ProductAdapter
import com.mahmutgunduz.westy.data.model.Product
import com.mahmutgunduz.westy.dataBase.FavoritesDao
import com.mahmutgunduz.westy.dataBase.FavoritesDataBase
import com.mahmutgunduz.westy.databinding.FragmentProductBinding
import com.mahmutgunduz.westy.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable

@AndroidEntryPoint
class ProductFragment : Fragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var dao: FavoritesDao
    private var compositeDisposable = CompositeDisposable()
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Room veritabanı bağlantısı
        dao = FavoritesDataBase.getDatabase(requireContext()).favoritesDao()
        compositeDisposable = CompositeDisposable()
        
        // RecyclerView ayarları
        setupRecyclerView()
        
        // ViewModel'den gelen verileri gözlemle
        observeViewModel()
        
        // Kategori ID'sini al ve ürünleri yükle
        val selectedCategoryId = arguments?.getInt("selectedCategoryId") ?: -1
        loadProductsByCategory(selectedCategoryId)
    }
    
    private fun setupRecyclerView() {
        binding.recyclerViewProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            productAdapter = ProductAdapter(emptyList(), requireContext(), dao)
            adapter = productAdapter
        }
    }
    
    private fun observeViewModel() {
        // Ürünleri gözlemle
        viewModel.products.observe(viewLifecycleOwner) { products ->
            updateUI(products)
        }
        
        // Yükleme durumunu gözlemle
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        // Hata durumunu gözlemle
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }
    
    private fun loadProductsByCategory(categoryId: Int) {
        if (categoryId >= 0) {
            val categoryName = viewModel.formatCategoryName(categoryId)
            if (categoryName.isNotEmpty()) {
                viewModel.loadProductsByCategory(categoryName)
            } else {
                viewModel.loadProducts() // Geçersiz kategori ID'si için tüm ürünleri yükle
            }
        } else {
            viewModel.loadProducts() // Kategori ID'si yoksa tüm ürünleri yükle
        }
    }
    
    private fun updateUI(products: List<Product>) {
        // Ürünleri adapter'a gönder
        productAdapter.updateProducts(products)
        
        // Ürün yoksa boş durum mesajını göster
        binding.emptyStateLayout.visibility = if (products.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        _binding = null
    }
}