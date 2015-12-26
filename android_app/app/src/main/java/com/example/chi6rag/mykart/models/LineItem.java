package com.example.chi6rag.mykart.models;

import com.google.gson.annotations.SerializedName;

public class LineItem {
    Integer id;
    Integer quantity;
    @SerializedName("variant_id")
    Integer variantId;
    Variant variant;
    @SerializedName("single_display_amount")
    String singleDisplayAmount;
    @SerializedName("display_amount")
    String displayAmount;
}
