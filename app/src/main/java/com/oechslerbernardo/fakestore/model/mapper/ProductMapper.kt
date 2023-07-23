package com.oechslerbernardo.fakestore.model.mapper

import com.oechslerbernardo.fakestore.model.network.NetworkProduct
import com.oechslerbernardo.fakestore.model.domain.Product
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    // Building a Product object from what we have in our networkProduct(backend) class
    fun buildFrom(networkProduct: NetworkProduct): Product {
        return Product(
            category = networkProduct.category,
            description = networkProduct.description,
            id = networkProduct.id,
            image = networkProduct.image,
            price = BigDecimal(networkProduct.price).setScale(2, RoundingMode.HALF_UP),// Used for currency and etc.
            title = networkProduct.title
        )
    }
}