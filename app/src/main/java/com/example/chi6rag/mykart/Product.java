package com.example.chi6rag.mykart;

import java.util.ArrayList;

public class Product {
    public String name;
    public String description;
    public double price;
    public String category;
    public int[] imageResources;

    public Product(String name, String description, double price, String category, int[] imageResources) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageResources = imageResources;
    }
}
