package com.example.chi6rag.mykart.models;

import android.content.Context;
import android.content.SharedPreferences;

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

    public static String current_number(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(Order.TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Order.CURRENT_NUMBER_KEY, null);
    }
}
