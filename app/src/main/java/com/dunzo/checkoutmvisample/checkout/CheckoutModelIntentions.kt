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

class CheckoutModelIntentions(
        private val addOneEvents: Observable<AddOneEvent> // RecyclerView
) {
    fun addOne(): Observable<AddOneEvent> =
            addOneEvents
}
