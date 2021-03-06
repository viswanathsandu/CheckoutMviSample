package com.dunzo.checkoutmvisample.checkout

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartItem(
        val product: Product,
        var quantity: Int
) : Parcelable
