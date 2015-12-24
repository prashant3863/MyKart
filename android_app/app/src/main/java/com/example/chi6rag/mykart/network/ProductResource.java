package com.example.chi6rag.mykart.network;

import com.example.chi6rag.mykart.models.Product;
import com.google.gson.annotations.SerializedName;

public class ProductResource {
    public Integer id;
    public String name;
    public String description;

    @SerializedName("master")
    public Product product;

    public ProductResource(Product product, String description, String name, Integer id) {
        this.product = product;
        this.description = description;
        this.name = name;
        this.id = id;
    }
}
