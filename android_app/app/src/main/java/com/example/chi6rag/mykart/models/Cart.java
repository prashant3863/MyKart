package com.example.chi6rag.mykart.models;

import android.content.Context;
import android.util.Log;

import com.example.chi6rag.mykart.async_tasks.AddProductToCartTask;
import com.example.chi6rag.mykart.async_tasks.Callback;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart cartInstance;

    private final Order order;
    private final Context context;
    private List<LineItem> lineItems;

    public static Cart getInstance(Context context, Order order) {
        if (cartInstance == null) {
            cartInstance = new Cart(context, order);
        }
        return cartInstance;
    }

    public void addProduct(final Product product) {
        if (this.order.isValid()) {
            executeAddProductToCartTask(product);
        }
    }

    private Cart(Context context, Order order) {
        this.context = context;
        this.order = order;
        this.lineItems = new ArrayList<LineItem>();
    }

    private void executeAddProductToCartTask(final Product product) {
        new AddProductToCartTask(
                order.number,
                order.token,
                product,
                new Callback<LineItem>() {
                    @Override
                    public void onSuccess(LineItem lineItem) {
                        cartInstance.addLineItem(lineItem);
                    }

                    @Override
                    public void onFailure() {
                        Log.d("chi6rag", "Failed to add product to cart");
                    }
                }).execute();
    }

    private void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }
}
