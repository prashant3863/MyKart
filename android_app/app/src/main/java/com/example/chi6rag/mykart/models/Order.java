package com.example.chi6rag.mykart.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.chi6rag.mykart.async_tasks.Callback;
import com.example.chi6rag.mykart.async_tasks.CreateOrderTask;
import com.google.gson.annotations.SerializedName;

public class Order {
    public static final String TAG = "com.example.chi6rag.mykart.models.ORDER_SHARED_PREFERENCES_TAG";
    public static final String CURRENT_NUMBER_KEY = "com.example.chi6rag.mykart.models.ORDER_CURRENT_KEY";
    public static final String CURRENT_TOKEN = "com.example.chi6rag.mykart.models.ORDER_CURRENT_TOKEN";

    public String number;
    public String token;
    Integer id;
    String email;
    String currency;
    @SerializedName("total_quantity")
    Integer totalQuantity;
    @SerializedName("bill_address")
    String billAddress;
    @SerializedName("ship_address")
    String shipAddress;

    public static void getCurrentInstance(final Context context, final Callback<Order> orderCallback) {
        String orderToken = fetchCurrentOrderToken(context);
        String orderNumber = fetchCurrentOrderNumber(context);

        if (orderNumber == null && orderToken == null) {
            new CreateOrderTask(new Callback<Order>() {
                @Override
                public void onSuccess(Order order) {
                    saveOrderTokenToSharedPreferences(context, order);
                    saveOrderNumberToSharedPreferences(context, order);
                }

                @Override
                public void onFailure() {
                    Log.d("chi6rag", "creation of order failed");
                }
            }, orderCallback).execute();
        } else {
            orderCallback.onSuccess(new Order(orderNumber, orderToken));
        }
    }

    private static void saveOrderTokenToSharedPreferences(Context context, Order order) {
        context.getSharedPreferences(Order.TAG, Context.MODE_PRIVATE).edit()
                .putString(Order.CURRENT_TOKEN, order.token)
                .commit();
    }

    private static void saveOrderNumberToSharedPreferences(Context context, Order order) {
        context.getSharedPreferences(Order.TAG, Context.MODE_PRIVATE).edit()
                .putString(Order.CURRENT_NUMBER_KEY, order.number)
                .commit();
    }

    private Order(String number, String token) {
        this.number = number;
        this.token = token;
    }

    private static String fetchCurrentOrderToken(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(Order.TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Order.CURRENT_TOKEN, null);
    }

    private static String fetchCurrentOrderNumber(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(Order.TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Order.CURRENT_NUMBER_KEY, null);
    }

    public boolean isValid() {
        return this.number != null && this.token != null;
    }
}
