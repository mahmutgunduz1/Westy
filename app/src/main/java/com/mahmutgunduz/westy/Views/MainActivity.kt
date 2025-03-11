package com.mahmutgunduz.westy.Views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mahmutgunduz.westy.fragments.AccountFragment
import com.mahmutgunduz.westy.fragments.CardFragment
import com.mahmutgunduz.westy.fragments.CategoriesFragment
import com.mahmutgunduz.westy.fragments.FavoritesFragment
import com.mahmutgunduz.westy.fragments.HomePageFragment
import com.mahmutgunduz.westy.fragments.CartFragment
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if we should navigate to the cart
        if (intent.getBooleanExtra("navigate_to_cart", false)) {
            replaceFragment(CardFragment())
            binding.bottomNavigationView.selectedItemId = R.id.cardFragment
        } else {
            replaceFragment(HomePageFragment())
        }

        // Set up bottom navigation
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homePageFragment -> replaceFragment(HomePageFragment())
                R.id.categoriesFragment -> replaceFragment(CategoriesFragment())
                R.id.cardFragment -> replaceFragment(CardFragment())
                R.id.favoritesFragment -> replaceFragment(FavoritesFragment())
                R.id.accountFragment -> replaceFragment(AccountFragment())
            }
            true
        }

        binding.bottomNavigationView.setOnItemReselectedListener { /* Aynı öğeye tekrar tıklandığında bir şey yapma */ }

        binding.bottomNavigationView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.price_color)
        binding.bottomNavigationView.itemTextColor = ContextCompat.getColorStateList(this, R.color.black)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}