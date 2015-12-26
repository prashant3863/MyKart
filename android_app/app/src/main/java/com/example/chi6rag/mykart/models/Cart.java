package com.example.chi6rag.mykart.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart cartInstance;
    private List<Product> productList;

    public static Cart getInstance() {
        if (cartInstance == null) {
            return new Cart();
        }
        return cartInstance;
    }

    private Cart() {
        productList = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        productList.add(product);
    }
}
