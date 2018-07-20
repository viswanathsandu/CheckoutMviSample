package com.dunzo.checkoutmvisample.checkout

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CartTest {
    @Test
    fun `when viewing an empty cart, then return empty list`() {
        val cart = Cart()
        val items = cart.getCartItems()

        assertThat(items)
                .isEmpty()
    }

    @Test
    fun ``
}
