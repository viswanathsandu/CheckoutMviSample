package com.dunzo.checkoutmvisample.checkout

class Cart {
    private val cartItems: MutableList<CartItem> = mutableListOf()

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
    }

    fun addOne(labelToAdd: String) {
        val cartItem = findItemInCart(labelToAdd)
        cartItem?.let { it.quantity++ }
    }

    fun removeOne(labelToRemove: String) {
        val cartItem = findItemInCart(labelToRemove)
        cartItem?.let { if (it.quantity > 0) it.quantity-- }
    }

    private fun findItemInCart(labelToAdd: String): CartItem? =
            cartItems.find { item -> item.product.label == labelToAdd }
}
