package com.example.chi6rag.mykart.models;

import com.example.chi6rag.mykart.R;

import java.util.List;

public class Product {
    private static final String RUPEES_SYMBOL = "Rs. ";
    public static String ID_TAG = String.valueOf(R.id.product_id_tag);

    public Integer id;
    public String name;
    public String description;
    public double price;
    public String category;
    public int[] imageResources;
    public List<Image> images;

    public Product(int id, String name, String description, double price, String category, int[] imageResources) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageResources = imageResources;
    }

    public Image firstImageResource() {
        if (images.size() > 0) {
            return images.get(0);
        }
        return null;
    }

    public String formattedPrice() {
        return RUPEES_SYMBOL + String.valueOf(this.price);
    }
}
