package com.dunzo.checkoutmvisample.checkout

import java.math.BigDecimal

interface CheckoutView {
    fun showTotalQuantity(totalQuantity: Int)
    fun showTotalPrice(totalPrice: BigDecimal)
    fun showCartItems(cartItems: List<CartItem>)
}
