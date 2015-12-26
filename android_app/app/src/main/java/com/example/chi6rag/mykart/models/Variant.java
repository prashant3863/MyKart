package com.example.chi6rag.mykart.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Variant {
    public Integer id;
    public String name;
    public List<Image> images;
    @SerializedName("display_price")
    public String displayPrice;
    @SerializedName("options_text")
    public String optionsText;
}
