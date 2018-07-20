package com.dunzo.checkoutmvisample.checkout

import java.math.BigDecimal

data class CartSummary(
        val totalQuantity: Int,
        val totalPrice: BigDecimal
)
