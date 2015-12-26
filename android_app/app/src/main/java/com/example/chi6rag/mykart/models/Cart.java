package com.example.chi6rag.mykart.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.chi6rag.mykart.async_tasks.AddProductToCartTask;
import com.example.chi6rag.mykart.async_tasks.CreateOrderTask;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart cartInstance;
    public String orderNumber;
    public String orderToken;
    private List<LineItem> lineItems;
    private final Context context;

    public static Cart getInstance(Context context) {
        if (cartInstance == null) {
            cartInstance = new Cart(context);
        }
        return cartInstance;
    }

    private Cart(Context context) {
        this.context = context;
        this.lineItems = new ArrayList<LineItem>();
    }

    public void addProduct(Product product) {
        this.orderNumber = fetchCurrentOrderNumber();
        if (this.orderNumber == null) {
            new CreateOrderTask(
                    this.context,
                    addProductSuccessCallback(product)
            ).execute();
        }
    }

    public void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }

    private AddProductToCartTask addProductSuccessCallback(Product product) {
        return new AddProductToCartTask(context, product, cartInstance);
    }

    private String fetchCurrentOrderNumber() {
        return Order.current_number(this.context);
    }

}
