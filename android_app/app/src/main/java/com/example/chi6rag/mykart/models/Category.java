package com.example.chi6rag.mykart.models;

import java.util.List;

public class Category {
    public Integer id;
    public String name;
    public List<ProductCategory> taxons;

    public Category(Integer id, String name, List<ProductCategory> taxons) {
        this.id = id;
        this.name = name;
        this.taxons = taxons;
    }
}
