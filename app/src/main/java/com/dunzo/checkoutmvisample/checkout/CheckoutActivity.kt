package com.dunzo.checkoutmvisample.checkout

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.dunzo.checkoutmvisample.MviApplication
import com.dunzo.checkoutmvisample.R
import com.dunzo.checkoutmvisample.mvi.MviActivity
import io.reactivex.Observable
import java.math.BigDecimal
import kotlin.LazyThreadSafetyMode.NONE

class CheckoutActivity : MviActivity<CheckoutState>(), CheckoutView {

    private val quantityTxt : TextView by lazy(NONE) { findViewById<TextView>(R.id.quantity) }

    private val priceTxt : TextView by lazy(NONE) { findViewById<TextView>(R.id.price) }

    private val cartAdapter by lazy(NONE) { CartAdapter() }

    private val intentions by lazy(NONE) {
        CheckoutIntentions(cartAdapter.changeQuantityEvents())
    }

    private val cart by lazy(NONE) {
        //FIXME(rj) 20/Jul/18 - Quick hack, this should be in `preBind()`
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        return@lazy (application as MviApplication).cart
    }

    private val viewDriver = CheckoutViewDriver(this)

    override fun layoutResId(): Int =
            R.layout.activity_checkout

    override fun bindingFunction(): () -> Observable<CheckoutState> =
            { CheckoutModel.bind(intentions, bindings, cart) }

    override fun renderFunction(): (CheckoutState) -> Unit =
            { state -> viewDriver.render(state) }

    override fun showCartItems(cartItems: List<CartItem>) {
        cartAdapter.updateCartItems(cartItems)
    }

    override fun showTotalQuantity(totalQuantity: Int) {
        quantityTxt.text = String.format("Quantity : %s", totalQuantity)
    }

    override fun showTotalPrice(totalPrice: BigDecimal) {
        priceTxt.text = String.format("Price : %s", totalPrice)
    }
}
