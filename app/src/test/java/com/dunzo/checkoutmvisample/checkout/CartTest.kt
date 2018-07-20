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

    @Test
    fun `when an item is not present in the cart, removing one does nothing`() {
        // when
        cart.removeOne(chocolate.label)
        val cartItems = cart.getCartItems()

        // then
        assertThat(cartItems)
                .isEmpty()
    }

    @Test
    fun `when an item is removed multiple times, then quantity cannot be negative`() {
        // when
        cart.addProduct(chocolate)
        cart.removeOne(chocolate.label)
        cart.removeOne(chocolate.label)
        cart.removeOne(chocolate.label)
        val cartItems = cart.getCartItems()

        // then
        val chocolateCartItem = CartItem(chocolate, 0)
        assertThat(cartItems)
                .containsExactly(chocolateCartItem)
    }

    @Test
    fun `when a product is added to the cart, then emit a cart summary`() {
        // when
        val testObserver = cart.summaries().test()

        cart.addProduct(chocolate)

        // then
        val cartSummary = CartSummary(1)
        with(testObserver) {
            assertNoErrors()
            assertValue(cartSummary)
            assertNotTerminated()
        }
    }

    @Test
    fun `when a product is incremented in the cart, then emit a cart summary`() {
        // when
        val testObserver = cart.summaries().test()
        cart.addProduct(chocolate)
        cart.addOne(chocolate.label)

        // then
        val addProductCartSummary = CartSummary(1)
        val addOneCartSummary = CartSummary(2)
        with(testObserver) {
            assertNoErrors()
            assertValues(addProductCartSummary, addOneCartSummary)
            assertNotTerminated()
        }
    }

    @Test
    fun `when a product is decremented in the cart, then emit a cart summary`() {
        // when
        val testObserver = cart.summaries().test()
        cart.addProduct(chocolate)
        cart.removeOne(chocolate.label)

        // then
        val addProductCartSummary = CartSummary(1)
        val removeOneCartSummary = CartSummary(0)
        with(testObserver) {
            assertNoErrors()
            assertValues(addProductCartSummary, removeOneCartSummary)
            assertNotTerminated()
        }
    }

    @Test
    fun `when subscribed to a non-empty cart, then emit a cart summary`() {
        // when
        cart.addProduct(chocolate)
        val testObserver = cart.summaries().test()

        // then
        val addProductCartSummary = CartSummary(1)
        with(testObserver) {
            assertNoErrors()
            assertValue(addProductCartSummary)
            assertNotTerminated()
        }
    }

    // TODO 4. Add price to product.
}
