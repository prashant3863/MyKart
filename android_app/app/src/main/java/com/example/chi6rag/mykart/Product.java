package com.example.chi6rag.mykart;

public class Product {
    public String name;
    public String description;
    public double price;
    public String category;
    public int[] imageResources;
    public int id;

    public Product(int id, String name, String description, double price, String category, int[] imageResources) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageResources = imageResources;
    }
}
