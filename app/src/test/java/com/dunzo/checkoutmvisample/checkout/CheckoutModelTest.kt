package com.dunzo.checkoutmvisample.checkout

import com.dunzo.checkoutmvisample.mvi.Binding
import com.dunzo.checkoutmvisample.mvi.Binding.CREATED
import com.dunzo.checkoutmvisample.mvi.Binding.RESTORED
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class CheckoutModelTest {
    private val bindings = PublishSubject.create<Binding>()
    private val cart: Cart = InMemoryCart()
    private lateinit var testObserver: TestObserver<CheckoutModelState>

    @Before
    fun setup() {
        cart.addProduct(Product("Chocolate", BigDecimal.TEN))
        cart.addProduct(Product("Whiskey", BigDecimal(100)))
        cart.addProduct(Product("Chakna", BigDecimal(5)))

        testObserver = CheckoutModel.bind(bindings, cart).test()
    }

    @Test
    fun `when screen is created, show items from the cart`() {
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

    @Test
    fun `when screen is restored, show items from the cart`() {
        // when
        screenIsRestored()

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

    private fun screenIsRestored() {
        bindings.onNext(RESTORED)
    }
}
