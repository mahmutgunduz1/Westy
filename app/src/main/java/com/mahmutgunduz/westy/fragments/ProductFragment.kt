package com.mahmutgunduz.westy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmutgunduz.westy.Model.BottomShetModelSubn
import com.mahmutgunduz.westy.adapters.ProductAdapter
import com.mahmutgunduz.westy.data.ProductLists
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

    companion object {
        private const val ARG_CATEGORY = "category"
        
        fun newInstance(category: String): ProductFragment {
            val fragment = ProductFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }

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

        // Kategori bilgisini al
        val category = arguments?.getString(ARG_CATEGORY) ?: ""
        
        // Kategoriye göre ürünleri göster
        val productList = ProductLists.getProductsForCategory(category)
        setupRecyclerView(productList)
    }

    private fun setupRecyclerView(list: List<BottomShetModelSubn>) {
        binding.recyclerViewProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            productAdapter = ProductAdapter(list, requireContext(), dao)
            adapter = productAdapter
        }

        // Ürün yoksa boş durum mesajını göster
        binding.emptyStateLayout.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        _binding = null
    }
}