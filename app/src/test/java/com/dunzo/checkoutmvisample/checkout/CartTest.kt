package com.dunzo.checkoutmvisample.checkout

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CartTest {
    private val cart = Cart()
    private val chocolate = Product("Chocolate")

    @Test
    fun `when viewing an empty cart, then return empty list`() {
        // when
        val cartItems = cart.getCartItems()

        // then
        assertThat(cartItems)
                .isEmpty()
    }

    @Test
    fun `when an item is added to the cart, it can be viewed`() {
        // when
        cart.addProduct(chocolate)
        val cartItems = cart.getCartItems()

        // then
        val chocolateCartItem = CartItem(chocolate, 1)
        assertThat(cartItems)
                .hasSize(1)
        assertThat(cartItems)
                .containsExactly(chocolateCartItem)
    }

    @Test
    fun `when the same item is added, increment its quantity in the cart`() {
        // when
        cart.addProduct(chocolate)
        cart.addProduct(chocolate)
        val cartItems = cart.getCartItems()

        // then
        val chocolateCartItem = CartItem(chocolate, 2)
        assertThat(cartItems)
                .containsExactly(chocolateCartItem)
    }

    @Test
    fun `when an item's quantity is increased, then add 1 more item to the cart`() {
        // when
        cart.addProduct(chocolate)
        cart.addOne(chocolate.label)
        cart.addOne(chocolate.label)
        val cartItems = cart.getCartItems()

        // then
        val chocolateCartItem = CartItem(chocolate, 3)
        assertThat(cartItems)
                .containsExactly(chocolateCartItem)
    }

    @Test
    fun `when an item is not present in the cart, adding one does nothing`() {
        // when
        cart.addOne(chocolate.label)
        val cartItems = cart.getCartItems()

        // then
        assertThat(cartItems)
                .isEmpty()
    }

    @Test
    fun `when an item's quantity is decreased, then remove 1 item from the cart`() {
        // when
        cart.addProduct(chocolate)
        cart.removeOne(chocolate.label)
        val cartItems = cart.getCartItems()

        // then
        val chocolateCartItem = CartItem(chocolate, 0)
        assertThat(cartItems)
                .containsExactly(chocolateCartItem)
    }
}
