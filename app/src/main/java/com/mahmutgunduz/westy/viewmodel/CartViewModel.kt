package com.mahmutgunduz.westy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmutgunduz.westy.data.model.Product
import com.mahmutgunduz.westy.dataBase.CartDao
import com.mahmutgunduz.westy.dataBase.CartData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartDao: CartDao
) : ViewModel() {
    
    private val _cartItems = MutableLiveData<List<CartData>>()
    val cartItems: LiveData<List<CartData>> = _cartItems
    
    private val _totalPrice = MutableLiveData<Double>(0.0)
    val totalPrice: LiveData<Double> = _totalPrice
    
    private val _itemCount = MutableLiveData<Int>(0)
    val itemCount: LiveData<Int> = _itemCount
    
    private val compositeDisposable = CompositeDisposable()
    
    init {
        loadCartItems()
    }
    
    private fun loadCartItems() {
        compositeDisposable.add(
            cartDao.getAllCartItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ items ->
                    _cartItems.value = items
                    calculateTotals(items)
                }, { error ->
                    // Handle error
                })
        )
    }
    
    private fun calculateTotals(items: List<CartData>) {
        var total = 0.0
        var count = 0
        
        items.forEach { item ->
            total += item.productPrice * item.quantity
            count += item.quantity
        }
        
        _totalPrice.value = total
        _itemCount.value = count
    }
    
    fun addToCart(product: Product) {
        viewModelScope.launch {
            // Check if product is already in cart
            cartDao.isInCart(product.id)
                .subscribeOn(Schedulers.io())
                .subscribe({ isInCart ->
                    if (isInCart) {
                        // Increment quantity
                        incrementQuantity(product.id)
                    } else {
                        // Add new item
                        val cartItem = CartData.fromProduct(product)
                        cartDao.insertCartItem(cartItem)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                loadCartItems()
                            }, { error ->
                                // Handle error
                            })
                    }
                }, { error ->
                    // Handle error
                })
        }
    }
    
    fun removeFromCart(productId: Int) {
        compositeDisposable.add(
            cartDao.deleteCartItemById(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loadCartItems()
                }, { error ->
                    // Handle error
                })
        )
    }
    
    fun incrementQuantity(productId: Int) {
        compositeDisposable.add(
            cartDao.incrementQuantity(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loadCartItems()
                }, { error ->
                    // Handle error
                })
        )
    }
    
    fun decrementQuantity(productId: Int) {
        compositeDisposable.add(
            cartDao.getQuantity(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ quantity ->
                    if (quantity > 1) {
                        cartDao.decrementQuantity(productId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                loadCartItems()
                            }, { error ->
                                // Handle error
                            })
                    } else {
                        removeFromCart(productId)
                    }
                }, { error ->
                    // Handle error
                })
        )
    }
    
    fun clearCart() {
        compositeDisposable.add(
            cartDao.clearCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loadCartItems()
                }, { error ->
                    // Handle error
                })
        )
    }
    
    fun updateTotalPrice(total: Double) {
        _totalPrice.value = total
    }
    
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
} 