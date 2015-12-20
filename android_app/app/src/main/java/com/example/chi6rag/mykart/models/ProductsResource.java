package com.example.chi6rag.mykart.models;

import java.util.List;

public class ProductsResource {
    public List<ProductResource> products;

    public ProductsResource(List<ProductResource> products) {
        this.products = products;
    }
}
