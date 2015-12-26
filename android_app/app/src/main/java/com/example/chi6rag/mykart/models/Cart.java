package com.example.chi6rag.mykart.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart cartInstance;
    private final Context context;
    private List<Product> productList;

    public static Cart getInstance(Context context) {
        if (cartInstance == null) {
            return new Cart(context);
        }
        return cartInstance;
    }

    private Cart(Context context) {
        this.context = context;
        productList = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        productList.add(product);
    }
}
