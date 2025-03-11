package com.mahmutgunduz.westy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.databinding.FragmentCartBinding

/**
 * This fragment is deprecated. All cart functionality has been moved to CardFragment.
 * This class remains for backward compatibility and redirects to CardFragment.
 */
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Redirect to CardFragment
        val cardFragment = CardFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, cardFragment)
            .commit()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 