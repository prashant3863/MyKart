package com.example.chi6rag.mykart.models;

import java.util.List;

public class CategoriesResource {
    public List<Category> taxonomies;

    public CategoriesResource(List<Category> taxonomies) {
        this.taxonomies = taxonomies;
    }

    public int length() {
        return taxonomies.size();
    }

    public Category findByPosition(int position) {
        return taxonomies.get(position);
    }
}
