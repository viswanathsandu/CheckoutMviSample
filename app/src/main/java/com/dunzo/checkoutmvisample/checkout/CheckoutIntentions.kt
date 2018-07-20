package com.dunzo.checkoutmvisample.checkout

import io.reactivex.Observable

/*
class CounterIntentions(
        private val incrementClicks: Observable<Unit>, // incrementButton.clicks()
        private val decrementClicks: Observable<Unit>  // decrementButton.clicks()
) {
    fun increment(): Observable<Int> =
            incrementClicks.map { +1 }

    fun decrement(): Observable<Int> =
            decrementClicks.map { -1 }
}
*/

class CheckoutIntentions(
        private val changeQuantityEvents: Observable<ChangeQuantityEvent> // RecyclerView
) {
    fun addOne(): Observable<AddOneEvent> =
            changeQuantityEvents.ofType(AddOneEvent::class.java)

    fun removeOne(): Observable<RemoveOneEvent> =
            changeQuantityEvents.ofType(RemoveOneEvent::class.java)
}
