package com.dunzo.checkoutmvisample.checkout

import com.dunzo.checkoutmvisample.mvi.Binding
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom

object CheckoutModel {
    fun bind(bindings: Observable<Binding>, cart: Cart): Observable<CheckoutModelState> {
        val createdUseCaseStates = bindings
                .withLatestFrom(cart.summaries()) { _, cartSummary -> cartSummary }
                .map { cartSummary -> CheckoutModelState(cart.getCartItems(), cartSummary) }

        return createdUseCaseStates
    }
}
