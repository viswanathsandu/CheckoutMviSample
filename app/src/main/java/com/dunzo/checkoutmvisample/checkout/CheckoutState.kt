package com.dunzo.checkoutmvisample.checkout

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckoutState(
        val items: List<CartItem>,
        val cartSummary: CartSummary
) : Parcelable
