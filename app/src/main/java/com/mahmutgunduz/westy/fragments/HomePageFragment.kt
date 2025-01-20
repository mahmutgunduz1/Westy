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
        horizontalList.addAll(listOf(
            HorizontalModel(R.drawable.photo9, "Yeni Sezon"),
            HorizontalModel(R.drawable.photo5, "Trend"),
            HorizontalModel(R.drawable.photo10, "Popüler"),
            HorizontalModel(R.drawable.photo12, "İndirimli"),
            HorizontalModel(R.drawable.photo12, "İndirimli"),
            HorizontalModel(R.drawable.photo12, "İndirimli"),
            HorizontalModel(R.drawable.photo12, "İndirimli")
        ))

        binding.featuredRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HorizontalAdapter(horizontalList)
        }
    }

    private fun setupVerticalRecyclerView() {
        verticalList.clear()
        verticalList.addAll(listOf(
            VerticalModel(R.drawable.turuncu),
            VerticalModel(R.drawable.photo4),
            VerticalModel(R.drawable.photo3),
            VerticalModel(R.drawable.turuncu),

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