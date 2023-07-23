package com.oechslerbernardo.fakestore.model.domain

import java.math.BigDecimal

data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: BigDecimal, // Used for currency and etc.
    val title: String
)
