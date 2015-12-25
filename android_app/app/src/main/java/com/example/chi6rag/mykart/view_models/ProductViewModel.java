package com.example.chi6rag.mykart.view_models;

import android.content.res.Resources;

import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.models.Product;

public class ProductViewModel {
    private final Product product;
    private final Resources resources;

    public ProductViewModel(Product product, Resources resources) {
        this.product = product;
        this.resources = resources;
    }

    public String formattedPrice() {
        return resources.getString(R.string.indian_currency) + product.price;
    }
}
