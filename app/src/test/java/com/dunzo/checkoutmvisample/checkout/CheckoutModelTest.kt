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
    private val cart: Cart = InMemoryCart()
    private val chocolate = Product("Chocolate", BigDecimal.TEN)
    private val whiskey = Product("Whiskey", BigDecimal(100))
    private val chakna = Product("Chakna", BigDecimal(5))

    private val changeQuantityEvents = PublishSubject.create<ChangeQuantityEvent>()
    private val bindings = PublishSubject.create<Binding>()
    private val intentions = CheckoutModelIntentions(changeQuantityEvents)
    private lateinit var testObserver: TestObserver<CheckoutModelState>

    @Before
    fun setup() {
        cart.addProduct(chocolate)
        cart.addProduct(whiskey)
        cart.addProduct(chakna)

        testObserver = CheckoutModel.bind(intentions, bindings, cart).test()
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

    @Test
    fun `when user clicks on add one, increase product quantity by 1`() {
        // when
        addOne(chocolate.label)
        val addOneMoreChocolateItems = cart.getCartItems()
        val addOneMoreChocolateSummary = CartSummary(4, BigDecimal(125))
        val addOneMoreChocolateState = CheckoutModelState(
                addOneMoreChocolateItems,
                addOneMoreChocolateSummary
        )

        // then
        with(testObserver) {
            assertNoErrors()
            assertValue(addOneMoreChocolateState) // TODO(rj) 20/Jun/18 - Ensure values are deep copied.
            assertNotTerminated()
        }
    }

    @Test
    fun `when user clicks on remove one, decrease product quantity by 1`() {
        // when
        removeOne(chocolate.label)
        val removeOneChocolateItems = cart.getCartItems()
        val removeOneChocolateSummary = CartSummary(2, BigDecimal(105))
        val removeOneChocolateState = CheckoutModelState(
                removeOneChocolateItems,
                removeOneChocolateSummary
        )

        // then
        with(testObserver) {
            assertNoErrors()
            assertValue(removeOneChocolateState) // TODO(rj) 20/Jun/18 - Ensure values are deep copied.
            assertNotTerminated()
        }
    }

    private fun screenIsCreated() {
        bindings.onNext(CREATED)
    }

    private fun screenIsRestored() {
        bindings.onNext(RESTORED)
    }

    private fun addOne(label: String) {
        changeQuantityEvents.onNext(AddOneEvent(label))
    }

    private fun removeOne(label: String) {
        changeQuantityEvents.onNext(RemoveOneEvent(label))
    }
}
