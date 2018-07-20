package com.dunzo.checkoutmvisample.checkout

import com.dunzo.checkoutmvisample.mvi.Binding
import com.dunzo.checkoutmvisample.mvi.Binding.CREATED
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom

object CheckoutModel {
    fun bind(bindings: Observable<Binding>, cart: Cart): Observable<CheckoutModelState> {
        val createdUseCaseStates = bindings
                .filter { it == CREATED }
                .withLatestFrom(cart.summaries()) { _, cartSummary -> cartSummary }
                .map { cartSummary -> CheckoutModelState(cart.getCartItems(), cartSummary) }

        return createdUseCaseStates
    }
}
