package com.dunzo.checkoutmvisample.checkout

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Product(
        val label: String,
        val price: BigDecimal
) : Parcelable
