package com.mahmutgunduz.westy.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.Views.ProductDetailsActivity
import com.mahmutgunduz.westy.adapters.CartAdapter
import com.mahmutgunduz.westy.dataBase.CartData
import com.mahmutgunduz.westy.databinding.FragmentCardBinding
import com.mahmutgunduz.westy.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    
    // Constants for shipping calculation
    private val SHIPPING_BASE_COST = 29.99
    private val FREE_SHIPPING_THRESHOLD = 500.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        observeViewModel()
        setupListeners()
    }
    
    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            emptyList(),
            requireContext(),
            onIncrement = { productId -> viewModel.incrementQuantity(productId) },
            onDecrement = { productId -> viewModel.decrementQuantity(productId) },
            onRemove = { productId -> viewModel.removeFromCart(productId) },
            onItemClick = { cartItem -> navigateToProductDetails(cartItem) }
        )
        
        binding.rcv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }
    
    private fun navigateToProductDetails(cartItem: CartData) {
        val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
        intent.putExtra("productId", cartItem.productId)
        startActivity(intent)
    }
    
    private fun observeViewModel() {
        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.updateList(items)
            updateUI(items.isEmpty())
            
            if (items.isNotEmpty()) {
                calculateAndDisplayPrices(items)
            }
        }
        
        viewModel.itemCount.observe(viewLifecycleOwner) { count ->
            // Update item count in the cart summary
            if (count > 0) {
                binding.cartSummary.visibility = View.VISIBLE
                binding.tvItemCount.text = resources.getQuantityString(
                    R.plurals.cart_item_count, count, count
                )
                
                // Show checkout layout when items are in cart
                binding.checkoutLayout.visibility = View.VISIBLE
            } else {
                binding.cartSummary.visibility = View.GONE
                binding.checkoutLayout.visibility = View.GONE
            }
        }
    }
    
    private fun calculateAndDisplayPrices(items: List<CartData>) {
        // Calculate subtotal
        val subtotal = items.sumOf { it.productPrice * it.quantity }
        
        // Calculate shipping cost
        val shippingCost = calculateShippingCost(subtotal)
        
        // Calculate total
        val total = subtotal + shippingCost
        
        // Format and display prices
        val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))
        
        binding.tvSubtotal.text = formatter.format(subtotal)
        binding.tvShipping.text = if (shippingCost > 0) 
            formatter.format(shippingCost) 
        else 
            getString(R.string.free_shipping)
        
        binding.tvTotal.text = formatter.format(total)
        binding.tvTotalAmount.text = formatter.format(total)
        
        // Update total in view model for other components to use
        viewModel.updateTotalPrice(total)
    }
    
    private fun calculateShippingCost(subtotal: Double): Double {
        return if (subtotal >= FREE_SHIPPING_THRESHOLD) 0.0 else SHIPPING_BASE_COST
    }
    
    private fun updateUI(isEmpty: Boolean) {
        if (isEmpty) {
            // Show empty cart view
            binding.emptyCartLayout.visibility = View.VISIBLE
            binding.cartItemsLayout.visibility = View.GONE
            binding.checkoutLayout.visibility = View.GONE
        } else {
            // Show cart items
            binding.emptyCartLayout.visibility = View.GONE
            binding.cartItemsLayout.visibility = View.VISIBLE
            binding.checkoutLayout.visibility = View.VISIBLE
        }
    }
    
    private fun setupListeners() {
        // Continue shopping button
        binding.btnStartShopping.setOnClickListener {
            // Navigate to home page
            val homePageFragment = HomePageFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, homePageFragment)
                .commit()
            
            // Set bottom navigation to home page
            (requireActivity() as? com.mahmutgunduz.westy.Views.MainActivity)?.let { mainActivity ->
                mainActivity.binding.bottomNavigationView.selectedItemId = R.id.homePageFragment
            }
        }
        
        // Clear cart button
        binding.btnClearCart.setOnClickListener {
            viewModel.clearCart()
            Toast.makeText(requireContext(), getString(R.string.cart_cleared), Toast.LENGTH_SHORT).show()
        }
        
        // Checkout button
        binding.btnCheckout.setOnClickListener {
            proceedToCheckout()
        }
    }
    
    private fun proceedToCheckout() {
        // Verify cart is valid before proceeding
        viewModel.cartItems.value?.let { items ->
            if (items.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.cart_empty), Toast.LENGTH_SHORT).show()
                return
            }
            
            // Here you would navigate to checkout/payment screen
            // For now, just show a message
            Toast.makeText(requireContext(), getString(R.string.checkout_not_implemented), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}