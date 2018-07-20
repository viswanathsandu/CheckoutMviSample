package com.dunzo.checkoutmvisample.checkout

class Cart {
    private val items: MutableList<Item> = mutableListOf()

    fun getCartItems(): List<Item> =
        items

    fun addItem(item: Item) {
        items.add(item)
    }
}
