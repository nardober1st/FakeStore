package com.oechslerbernardo.fakestore

import com.airbnb.epoxy.TypedEpoxyController
import com.oechslerbernardo.fakestore.model.domain.Product

class ProductEpoxyController : TypedEpoxyController<List<Product>>() {

    override fun buildModels(data: List<Product>?) {
//        if (data == null || data.isEmpty())
            if (data.isNullOrEmpty()) {
            // todo loading state
            return
        }

        data.forEach { product ->
            ProductEpoxyModel(product).id(product.id).addTo(this)
        }
    }
}