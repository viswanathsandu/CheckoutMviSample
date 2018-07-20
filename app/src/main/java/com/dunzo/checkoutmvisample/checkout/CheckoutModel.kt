package com.dunzo.checkoutmvisample.checkout

import com.dunzo.checkoutmvisample.mvi.Binding
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom

object CheckoutModel {
    fun bind(
            intentions: CheckoutModelIntentions,
            bindings: Observable<Binding>,
            cart: Cart
    ): Observable<CheckoutModelState> {
        return Observable.merge(
                bindingsUseCase(bindings, cart),
                addOneUseCase(intentions.addOne(), cart)
        )
    }

    private fun bindingsUseCase(
            bindings: Observable<Binding>,
            cart: Cart
    ): Observable<CheckoutModelState> {
        return bindings
                .withLatestFrom(cart.summaries()) { _, cartSummary -> cartSummary }
                .map { cartSummary -> CheckoutModelState(cart.getCartItems(), cartSummary) }
    }

    private fun addOneUseCase(
            addOneEvents: Observable<AddOneEvent>,
            cart: Cart
    ): Observable<CheckoutModelState>? {
        return addOneEvents
                .map { addOneEvent -> addOneEvent.label }
                .switchMap { Observable.fromCallable { cart.addOne(it) } }
                .withLatestFrom(cart.summaries()) { _, cartSummary -> cartSummary }
                .map { cartSummary -> CheckoutModelState(cart.getCartItems(), cartSummary) }
    }
}
