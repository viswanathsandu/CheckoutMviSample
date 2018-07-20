package com.dunzo.checkoutmvisample.checkout

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import org.junit.Test
import java.math.BigDecimal

class CheckoutViewDriverTest {
    @Test
    fun `when a state is available, show items along with total quantity and pricing`() {
        // given
        val view = mock<CheckoutView>()
        val checkoutViewDriver = CheckoutViewDriver(view)

        val chocolate = Product("Chocolate", BigDecimal.TEN)
        val cartItems = listOf(CartItem(chocolate, 1))
        val cartSummary = CartSummary(1, chocolate.price)
        val state = CheckoutState(cartItems, cartSummary)

        // when
        checkoutViewDriver.render(state)

        // then
        verify(view).showCartItems(cartItems)
        verify(view).showTotalQuantity(cartSummary.totalQuantity)
        verify(view).showTotalPrice(cartSummary.totalPrice)
        verifyNoMoreInteractions(view)
    }
}
