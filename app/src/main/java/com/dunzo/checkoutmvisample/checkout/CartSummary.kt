package com.dunzo.checkoutmvisample.checkout

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class CartSummary(
        val totalQuantity: Int,
        val totalPrice: BigDecimal
) : Parcelable
