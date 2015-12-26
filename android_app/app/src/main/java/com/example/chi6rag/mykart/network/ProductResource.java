package com.example.chi6rag.mykart.network;

import com.example.chi6rag.mykart.models.Product;
import com.example.chi6rag.mykart.models.Variant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResource {
    public Integer id;
    public String name;
    public String description;

    @SerializedName("master")
    public Product product;
    public List<Variant> variants;

    public ProductResource(Product product, String description, String name, Integer id, List<Variant> variants) {
        this.product = product;
        this.description = description;
        this.name = name;
        this.id = id;
        this.variants = variants;
    }
}
