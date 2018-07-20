package com.dunzo.checkoutmvisample.checkout

class CheckoutViewDriver(private val view: CheckoutView) {
    fun render(state: CheckoutState) {
        view.showCartItems(state.items)
        view.showTotalQuantity(state.cartSummary.totalQuantity)
        view.showTotalPrice(state.cartSummary.totalPrice)
    }
}
