package com.dunzo.checkoutmvisample

import android.app.Application
import com.dunzo.checkoutmvisample.checkout.Cart
import com.dunzo.checkoutmvisample.checkout.InMemoryCart
import com.dunzo.checkoutmvisample.checkout.Product
import java.math.BigDecimal

class MviApplication : Application() {
    val cart: Cart = InMemoryCart()

    override fun onCreate() {
        super.onCreate()
        cart.addProduct(Product("CHOCOLATE", BigDecimal.TEN))
        cart.addProduct(Product("WHISKY", BigDecimal(100)))
        cart.addProduct(Product("CHAKNA", BigDecimal(5)))
    }
}
