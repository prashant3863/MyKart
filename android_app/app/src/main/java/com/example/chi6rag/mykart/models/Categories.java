package com.example.chi6rag.mykart.models;

import java.util.List;

public class Categories {
    public List<Category> taxonomies;

    public Categories(List<Category> taxonomies) {
        this.taxonomies = taxonomies;
    }

    public int length() {
        return taxonomies.size();
    }

    public Category findByPosition(int position) {
        return taxonomies.get(position);
    }
}
