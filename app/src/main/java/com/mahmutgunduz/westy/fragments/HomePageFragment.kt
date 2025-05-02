package com.mahmutgunduz.westy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.adapters.ViewPagerAdapter
import com.mahmutgunduz.westy.adapters.HorizontalAdapter
import com.mahmutgunduz.westy.adapters.VerticalAdapter
import com.mahmutgunduz.westy.Model.HorizontalModel
import com.mahmutgunduz.westy.Model.VerticalModel
import com.mahmutgunduz.westy.databinding.FragmentHomePageBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomePageFragment : Fragment() {
    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val sliderImages = ArrayList<Int>()
    private var autoScrollJob: Job? = null
    private var pageChangeCallback: ViewPager2.OnPageChangeCallback? = null
    private var indicators = ArrayList<ImageView>()
    
    private val horizontalList = ArrayList<HorizontalModel>()
    private val verticalList = ArrayList<VerticalModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        initializeSliderImages()
        setupViewPager()
        setupHorizontalRecyclerView()
        setupVerticalRecyclerView()
    }

    private fun initializeSliderImages() {
        sliderImages.clear()
        sliderImages.addAll(listOf(
            R.drawable.viewpagerphoto,
            R.drawable.viewpagerphoto2,

        ))
    }

    private fun setupViewPager() {
        if (sliderImages.isEmpty()) return

        viewPagerAdapter = ViewPagerAdapter(sliderImages)
        setupIndicators()

        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val realPosition = viewPagerAdapter.getRealPosition(position)
                updateIndicators(realPosition)
            }
        }

        with(binding) {
            viewPager2.apply {
                adapter = viewPagerAdapter
                orientation = ViewPager2.ORIENTATION_HORIZONTAL

                val startPosition = sliderImages.size * 100
                setCurrentItem(startPosition, false)

                pageChangeCallback?.let { callback ->
                    registerOnPageChangeCallback(callback)
                }
            }
        }

        startAutoScroll()
    }

    private fun setupIndicators() {
        binding.tabLayout.removeAllTabs()
        
        for (i in sliderImages.indices) {
            binding.tabLayout.addTab(binding.tabLayout.newTab())
        }
    }

    private fun updateIndicators(position: Int) {
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
    }

    private fun startAutoScroll() {
        autoScrollJob?.cancel()
        autoScrollJob = viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    delay(5000)
                    with(binding) {
                        viewPager2.let { viewPager ->
                            if (viewPager.adapter?.itemCount ?: 0 > 0) {
                                val nextItem = viewPager.currentItem + 1
                                viewPager.setCurrentItem(nextItem, true)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupHorizontalRecyclerView() {
        horizontalList.clear()
        horizontalList.addAll(
            listOf(
                HorizontalModel(
                    id = 1,
                    img = R.drawable.photo9, 
                    txt = "Basic Tişört", 
                    price = 199.99, 
                    oldPrice = 249.99, 
                    discountInfo = "Yeni Sezon"
                ),
                HorizontalModel(
                    id = 2,
                    img = R.drawable.photo5, 
                    txt = "Siyah Kapüşonlu", 
                    price = 329.99, 
                    oldPrice = 379.99, 
                    discountInfo = "Trend"
                ),
                HorizontalModel(
                    id = 7,
                    img = R.drawable.kazak1, 
                    txt = "Beyaz Kazak", 
                    price = 399.99, 
                    oldPrice = 449.99, 
                    discountInfo = "Kış Koleksiyonu"
                ),
                HorizontalModel(
                    id = 16,
                    img = R.drawable.pantalon1, 
                    txt = "Kot Pantolon", 
                    price = 299.99, 
                    oldPrice = 349.99, 
                    discountInfo = "Yeni Sezon"
                ),
                HorizontalModel(
                    id = 34,
                    img = R.drawable.spor1, 
                    txt = "Beyaz Spor Ayakkabı", 
                    price = 399.99, 
                    oldPrice = 449.99, 
                    discountInfo = "Son Stok"
                ),
                HorizontalModel(
                    id = 58,
                    img = R.drawable.goz1, 
                    txt = "Güneş Gözlüğü", 
                    price = 199.99, 
                    oldPrice = 249.99, 
                    discountInfo = "Yaz Koleksiyonu"
                ),
                HorizontalModel(
                    id = 70,
                    img = R.drawable.mont1, 
                    txt = "Kışlık Mont", 
                    price = 599.99, 
                    oldPrice = 699.99, 
                    discountInfo = "Sezon Sonu"
                )
            )
        )

        binding.featuredRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HorizontalAdapter(horizontalList, requireContext())
        }
    }

    private fun setupVerticalRecyclerView() {
        verticalList.clear()
        verticalList.addAll(listOf(
            VerticalModel(
                img = R.drawable.turuncu,
                categoryName = "Bluzlar",
                discountLabel = "20% İndirim"
            ),
            VerticalModel(
                img = R.drawable.photo4,
                categoryName = "Sweatshirtler",
                discountLabel = "Özel Fiyat"
            ),
            VerticalModel(
                img = R.drawable.photo3,
                categoryName = "Montlar",
                discountLabel = "Outlet"
            ),
            VerticalModel(
                img = R.drawable.turuncu,
                categoryName = "Spor Ayakkabılar",
                discountLabel = "2 Al 1 Öde"
            )
        ))

        binding.allProductsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = VerticalAdapter(verticalList, parentFragmentManager)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        autoScrollJob?.cancel()
        pageChangeCallback?.let { callback ->
            binding.viewPager2.unregisterOnPageChangeCallback(callback)
        }
        pageChangeCallback = null
        _binding = null
        super.onDestroyView()
    }
}