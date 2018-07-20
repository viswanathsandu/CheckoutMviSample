package com.dunzo.checkoutmvisample.checkout

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class Cart {
    private val cartItems: MutableList<CartItem> = mutableListOf()
    private val cartSummarySubject = PublishSubject.create<CartSummary>()

    fun getCartItems(): List<CartItem> =
            cartItems

    fun addProduct(product: Product) {
        val labelToFind = product.label
        val cartItem = findItemInCart(labelToFind)

        if (cartItem == null) {
            cartItems.add(CartItem(product, 1))
        } else {
            cartItem.quantity++
        }

        cartSummarySubject.onNext(getCartSummary(cartItems))
    }

    fun addOne(labelToAdd: String) {
        val cartItem = findItemInCart(labelToAdd)
        cartItem?.let { it.quantity++ }
    }

    fun removeOne(labelToRemove: String) {
        val cartItem = findItemInCart(labelToRemove)
        cartItem?.let { if (it.quantity > 0) it.quantity-- }
    }

    fun summaries(): Observable<CartSummary> =
            cartSummarySubject

    private fun findItemInCart(labelToAdd: String): CartItem? =
            cartItems.find { item -> item.product.label == labelToAdd }

    private fun getCartSummary(cartItems: List<CartItem>): CartSummary {
        val totalQuantity = cartItems.fold(0) {
            previousQuantity, cartItem -> previousQuantity + cartItem.quantity
        }
        return CartSummary(totalQuantity)
    }
}
