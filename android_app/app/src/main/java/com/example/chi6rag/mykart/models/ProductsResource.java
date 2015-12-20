package com.example.chi6rag.mykart.models;

import java.util.ArrayList;

public class ProductsResource {
    public ArrayList<ProductResource> products;

    public ProductResource get(Integer index) {
        return this.products.get(index);
    }

    public ProductsResource() {
        this.products = new ArrayList<ProductResource>();
    }

    public int size() {
        return this.products.size();
    }

    public void addAll(ProductsResource productsResource) {
        this.products.addAll(productsResource.products);
    }
}
