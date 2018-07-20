package com.dunzo.checkoutmvisample.checkout

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class InMemoryCart : Cart {
    private val cartItems: MutableList<CartItem> = mutableListOf()
    private val cartSummarySubject = BehaviorSubject.createDefault<CartSummary>(getCartSummary(cartItems))

    override fun getCartItems(): List<CartItem> =
            cartItems

    override fun addProduct(product: Product) {
        val labelToFind = product.label
        val cartItem = findItemInCart(labelToFind)

        if (cartItem == null) {
            cartItems.add(CartItem(product, 1))
        } else {
            cartItem.quantity++
        }

        publishCartUpdate()
    }

    override fun addOne(labelToAdd: String) {
        val cartItem = findItemInCart(labelToAdd)
        cartItem?.let {
            it.quantity++
            publishCartUpdate()
        }
    }

    override fun removeOne(labelToRemove: String) {
        val cartItem = findItemInCart(labelToRemove)
        cartItem?.let {
            if (it.quantity > 0) it.quantity--
            publishCartUpdate()
        }
    }

    override fun summaries(): Observable<CartSummary> =
            cartSummarySubject.toFlowable(BackpressureStrategy.LATEST).toObservable()

    private fun findItemInCart(labelToAdd: String): CartItem? =
            cartItems.find { item -> item.product.label == labelToAdd }

    private fun getCartSummary(cartItems: List<CartItem>): CartSummary {
        val totalQuantity = cartItems.fold(0) {
            previousQuantity, cartItem -> previousQuantity + cartItem.quantity
        }
        return CartSummary(totalQuantity)
    }

    private fun publishCartUpdate() {
        cartSummarySubject.onNext(getCartSummary(cartItems))
    }
}
