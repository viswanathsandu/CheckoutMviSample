package com.dunzo.checkoutmvisample.checkout

import com.dunzo.checkoutmvisample.mvi.Binding
import com.dunzo.checkoutmvisample.mvi.Binding.CREATED
import io.reactivex.subjects.PublishSubject
import org.junit.Test
import java.math.BigDecimal

class CheckoutModelTest {
    val bindings = PublishSubject.create<Binding>()

    @Test
    fun `when screen is created, show items from the cart`() {
        // given
        val cart: Cart = InMemoryCart()
        cart.addProduct(Product("Chocolate", BigDecimal.TEN))
        cart.addProduct(Product("Whiskey", BigDecimal(100)))
        cart.addProduct(Product("Chakna", BigDecimal(5)))

        val testObserver = CheckoutModel.bind(bindings, cart).test()

        // when
        screenIsCreated()

        // then
        val items = cart.getCartItems()
        val cartSummary = CartSummary(3, BigDecimal(115))
        with(testObserver) {
            assertNoErrors()
            assertValue(CheckoutModelState(items, cartSummary))
            assertNotTerminated()
        }
    }

    private fun screenIsCreated() {
        bindings.onNext(CREATED)
    }
}
