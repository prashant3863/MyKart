package com.example.chi6rag.mykart.models;

import com.google.gson.annotations.SerializedName;

public class Order {
    public static final String TAG = "com.example.chi6rag.mykart.models.ORDER_SHARED_PREFERENCES_TAG";
    public static final String CURRENT_NUMBER_KEY = "com.example.chi6rag.mykart.models.ORDER_CURRENT_KEY";
    public String number;
    Integer id;
    String email;
    String currency;
    @SerializedName("total_quantity")
    Integer totalQuantity;
    String token;
    @SerializedName("bill_address")
    String billAddress;
    @SerializedName("ship_address")
    String shipAddress;
}
