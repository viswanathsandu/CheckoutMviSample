package com.dunzo.checkoutmvisample.checkout

class Cart {
    private val cartItems: MutableList<CartItem> = mutableListOf()

    fun getCartItems(): List<CartItem> =
            cartItems

    fun addProduct(product: Product) {
        val labelToFind = product.label
        val cartItem = cartItems.find { item -> item.product.label == labelToFind }

        if (cartItem == null) {
            cartItems.add(CartItem(product, 1))
        } else {
            cartItem.quantity++
        }
    }
}
