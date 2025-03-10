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
import com.mahmutgunduz.westy.R
import com.mahmutgunduz.westy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(HomePageFragment()) // Başlangıçta HomePageFragment'i yükle
        }
        binding.bottomNavigationView.apply {
            setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.homePageFragment -> {
                        replaceFragment(HomePageFragment())
                        true
                    }
                    R.id.categoriesFragment -> {
                        replaceFragment(CategoriesFragment())
                        true
                    }
                    R.id.favoritesFragment -> {
                        replaceFragment(FavoritesFragment())
                        true
                    }
                    R.id.cardFragment -> {
                        replaceFragment(CardFragment())
                        true
                    }
                    R.id.accountFragment -> {
                        replaceFragment(AccountFragment())
                        true
                    }
                    else -> false
                }
            }
            
            setOnNavigationItemReselectedListener { /* Aynı öğeye tekrar tıklandığında bir şey yapma */ }
        }

        binding.bottomNavigationView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.price_color)
        binding.bottomNavigationView.itemTextColor = ContextCompat.getColorStateList(this, R.color.black)
    }

    fun replaceFragment(fragment: Fragment) {
        val framentManager = supportFragmentManager
        val fragmentTransaction = framentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
}