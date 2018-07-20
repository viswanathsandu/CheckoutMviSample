package com.dunzo.checkoutmvisample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dunzo.checkoutmvisample.checkout.CartItem
import com.dunzo.checkoutmvisample.checkout.CheckoutView
import java.math.BigDecimal

class CheckoutActivity : AppCompatActivity(), CheckoutView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
    }

    override fun showCartItems(cartItems: List<CartItem>) {
        TODO("not implemented")
    }

    override fun showTotalQuantity(totalQuantity: Int) {
        TODO("not implemented")
    }

    override fun showTotalPrice(totalPrice: BigDecimal) {
        TODO("not implemented")
    }
}
