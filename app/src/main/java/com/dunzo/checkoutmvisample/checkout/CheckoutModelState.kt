package com.dunzo.checkoutmvisample.checkout

data class CheckoutModelState(
        val items: List<CartItem>,
        val cartSummary: CartSummary
)
