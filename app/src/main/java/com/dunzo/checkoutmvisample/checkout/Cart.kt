package com.dunzo.checkoutmvisample.checkout

import io.reactivex.Observable

interface Cart {
    fun getCartItems(): List<CartItem>
    fun addProduct(product: Product)
    fun addOne(labelToAdd: String)
    fun removeOne(labelToRemove: String)
    fun summaries(): Observable<CartSummary>
}
