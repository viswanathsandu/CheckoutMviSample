package com.dunzo.checkoutmvisample.checkout

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CartTest {
    @Test
    fun `when viewing an empty cart, then return empty list`() {
        // given
        val cart = Cart()

        // when
        val items = cart.getCartItems()

        // then
        assertThat(items)
                .isEmpty()
    }

    @Test
    fun `when an item is added to the cart, it can be viewed`() {
        // given
        val cart = Cart()
        val chocolate = Item("Chocolate")

        // when
        cart.addItem(chocolate)
        val items = cart.getCartItems()

        // then
        assertThat(items)
                .hasSize(1)
        assertThat(items)
                .containsExactly(chocolate)
    }
}
