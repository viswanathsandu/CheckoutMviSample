package com.dunzo.checkoutmvisample.checkout

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.dunzo.checkoutmvisample.R
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CartAdapter : RecyclerView.Adapter<CartItemViewHolder>() {
    private val changeQuantityEventsSubject = PublishSubject.create<ChangeQuantityEvent>()

    private val mutableCartItems = mutableListOf<CartItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val rootView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.cart_item_layout, parent, false)

        return CartItemViewHolder(rootView, changeQuantityEventsSubject)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(mutableCartItems.get(position))
    }

    override fun getItemCount(): Int =
            mutableCartItems.size

    fun updateCartItems(cartItems: List<CartItem>) {
        mutableCartItems.clear()
        mutableCartItems.addAll(cartItems)
        notifyDataSetChanged()
    }

    fun changeQuantityEvents(): Observable<ChangeQuantityEvent> =
            changeQuantityEventsSubject.toFlowable(BackpressureStrategy.LATEST).toObservable()
}

class CartItemViewHolder(
        private val view: View,
        private val changeQuantityEvents: PublishSubject<ChangeQuantityEvent>
) : RecyclerView.ViewHolder(view) {
    fun bind(cartItem: CartItem) {
        val product = cartItem.product

        view.findViewById<TextView>(R.id.title_txt).text = product.label
        view.findViewById<TextView>(R.id.quantity_txt).text = cartItem.quantity.toString()

        view.findViewById<Button>(R.id.add_btn).setOnClickListener {
            changeQuantityEvents.onNext(AddOneEvent(product.label))
        }

        view.findViewById<Button>(R.id.remove_btn).setOnClickListener {
            changeQuantityEvents.onNext(RemoveOneEvent(product.label))
        }
    }
}
