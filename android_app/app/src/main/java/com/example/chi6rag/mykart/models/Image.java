package com.example.chi6rag.mykart.models;

import com.google.gson.annotations.SerializedName;

public class Image {
    public Integer id;
    public String position;

    @SerializedName("attachment_width")
    public Integer attachmentWidth;
    @SerializedName("attachment_height")
    public Integer attachmentHeight;
    @SerializedName("mini_url")
    public String miniUrl;
    @SerializedName("small_url")
    public String smallUrl;
    @SerializedName("product_url")
    public String productUrl;
    @SerializedName("large_url")
    public String largeUrl;
}
