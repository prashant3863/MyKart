package com.example.chi6rag.mykart.models;

import android.content.Context;
import android.util.Log;

import com.example.chi6rag.mykart.async_tasks.AddProductToCartTask;
import com.example.chi6rag.mykart.async_tasks.Callback;
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

    public void addProduct(final Product product) {
        this.orderNumber = fetchCurrentOrderNumber();
        if (this.orderNumber == null || this.orderToken == null) {
            new CreateOrderTask(new Callback<Order>() {
                @Override
                public void onSuccess(Order order) {
                    saveOrderDetails(order);
                    executeAddProductToCartTask(product);
                }

                @Override
                public void onFailure() {
                    Log.d("chi6rag", "Failed to create an order");
                }
            }).execute();
        } else {
            executeAddProductToCartTask(product);
        }
    }

    private Cart(Context context) {
        this.context = context;
        this.lineItems = new ArrayList<LineItem>();
    }

    private void saveOrderDetails(Order order) {
        saveOrderTokenToSharedPreferences(order);
        saveOrderNumberToSharedPreferences(order);
        orderNumber = order.number;
        orderToken = order.token;
    }

    private void executeAddProductToCartTask(final Product product) {
        new AddProductToCartTask(
                orderNumber,
                orderToken,
                product,
                new Callback<LineItem>() {
                    @Override
                    public void onSuccess(LineItem lineItem) {
                        cartInstance.addLineItem(lineItem);
                        Log.d("chi6rag", orderNumber + ", " + orderToken + ": " + lineItems.size());
                    }

                    @Override
                    public void onFailure() {
                        Log.d("chi6rag", "Failed to add product to cart");
                    }
                }).execute();
    }

    private void saveOrderTokenToSharedPreferences(Order order) {
        this.context.getSharedPreferences(Order.TAG, Context.MODE_PRIVATE).edit()
                .putString(Order.CURRENT_TOKEN, order.token)
                .commit();
    }

    private void saveOrderNumberToSharedPreferences(Order order) {
        this.context.getSharedPreferences(Order.TAG, Context.MODE_PRIVATE).edit()
                .putString(Order.CURRENT_NUMBER_KEY, order.number)
                .commit();
    }

    private void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }

    private String fetchCurrentOrderNumber() {
        return Order.current_number(this.context);
    }
}
