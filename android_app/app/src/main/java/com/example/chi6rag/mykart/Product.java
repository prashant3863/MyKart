package com.example.chi6rag.mykart;

public class Product {
    private static final String RUPEES_SYMBOL = "Rs. ";
    public static String ID_TAG = String.valueOf(R.id.product_id_tag);
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

    public int firstImageResource() {
        if(imageResources.length > 0)
            return imageResources[0];
        return 0;
    }

    public String formattedPrice() {
        return RUPEES_SYMBOL + String.valueOf(this.price);
    }
}
