package com.dunzo.checkoutmvisample.checkout

sealed class ChangeQuantityEvent
data class AddOneEvent(val label: String) : ChangeQuantityEvent()
data class RemoveOneEvent(val label: String) : ChangeQuantityEvent()
