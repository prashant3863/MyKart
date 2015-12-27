package com.example.chi6rag.mykart.models;

import android.content.Context;
import android.util.Log;

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

    public void addProduct(final Product product) {
        clearSharedPreferences();
        this.orderNumber = fetchCurrentOrderNumber();
        if (this.orderNumber == null) {
            new CreateOrderTask(this.context, new CreateOrderTask.Callback() {
                @Override
                public void onSuccess(Order order) {
                    saveOrderTokenToSharedPreferences(order);
                    saveOrderNumberToSharedPreferences(order);
                    orderNumber = order.number;
                    orderToken = order.token;
                    executeAddProductToCartTask(product);
                }

                @Override
                public void onFailure() {
                    Log.d("chi6rag", "Failed to create an order");
                }
            }).execute();
        }
    }

    private void executeAddProductToCartTask(Product product) {
        new AddProductToCartTask(
                orderNumber,
                orderToken,
                product,
                new AddProductToCartTask.Callback() {
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

    private void clearSharedPreferences() {
        this.context.getSharedPreferences(Order.TAG, Context.MODE_PRIVATE).edit()
                .remove(Order.CURRENT_NUMBER_KEY)
                .remove(Order.CURRENT_TOKEN)
                .commit();
    }

    private void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }

    private String fetchCurrentOrderNumber() {
        return Order.current_number(this.context);
    }
}
