package com.mahmutgunduz.westy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmutgunduz.westy.adapters.CategoriesAdapter
import com.mahmutgunduz.westy.Model.CategoriesModel
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.databinding.FragmentCategoriesBinding


class CategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private  val categoriesList = ArrayList<CategoriesModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateCategoriesList()


        binding.rcv.layoutManager = LinearLayoutManager(requireContext())
        val postAdapter = CategoriesAdapter(categoriesList, requireContext())
        binding.rcv.adapter = postAdapter


    }
    private fun populateCategoriesList() {
        categoriesList.add(CategoriesModel(0, "Üst Giyim", R.drawable.tisortlan2))
        categoriesList.add(CategoriesModel(1, "Alt Giyim", R.drawable.pantss2))
        categoriesList.add(CategoriesModel(2, "Ayakkabılar", R.drawable.shoeee))
        categoriesList.add(CategoriesModel(3, "Aksesuarlar", R.drawable.hatt))
        categoriesList.add(CategoriesModel(4, "Dış Giyim", R.drawable.yelek2))

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }



}