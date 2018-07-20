package com.dunzo.checkoutmvisample.checkout

class CheckoutViewDriver(private val view: CheckoutView) {
    fun render(state: CheckoutModelState) {
        view.showCartItems(state.items)
        view.showTotalQuantity(state.cartSummary.totalQuantity)
        view.showTotalPrice(state.cartSummary.totalPrice)
    }
}
