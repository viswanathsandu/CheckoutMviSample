package com.dunzo.checkoutmvisample.checkout

import com.dunzo.checkoutmvisample.mvi.Binding
import io.reactivex.Observable

object CheckoutModel {
    fun bind(
            intentions: CheckoutIntentions,
            bindings: Observable<Binding>,
            cart: Cart
    ): Observable<CheckoutState> {
        return Observable.merge(
                bindingsUseCase(bindings, cart),
                addOneUseCase(intentions.addOne(), cart),
                removeOneUseCase(intentions.removeOne(), cart)
        )
    }

    private fun bindingsUseCase(
            bindings: Observable<Binding>,
            cart: Cart
    ): Observable<CheckoutState> {
        return bindings
                .switchMap { cart.summaries() }
                .map { cartSummary ->  CheckoutState(cart.getCartItems(), cartSummary) }
    }

    private fun addOneUseCase(
            addOneEvents: Observable<AddOneEvent>,
            cart: Cart
    ): Observable<CheckoutState> {
        return addOneEvents
                .map { addOneEvent -> addOneEvent.label }
                .flatMap { Observable.fromCallable { cart.addOne(it) } }
                .flatMap { Observable.empty<CheckoutState>() }
    }

    private fun removeOneUseCase(
            removeOneEvents: Observable<RemoveOneEvent>,
            cart: Cart
    ): Observable<CheckoutState> {
        return removeOneEvents
                .map { removeOneEvent -> removeOneEvent.label }
                .flatMap { Observable.fromCallable { cart.removeOne(it) } }
                .flatMap { Observable.empty<CheckoutState>() }
    }
}
