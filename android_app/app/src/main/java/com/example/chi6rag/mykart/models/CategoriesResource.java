package com.example.chi6rag.mykart.models;

import java.util.List;

public class CategoriesResource {
    public List<CategoryResource> taxonomies;

    public CategoriesResource(List<CategoryResource> taxonomies) {
        this.taxonomies = taxonomies;
    }

    public int length() {
        return taxonomies.size();
    }

    public CategoryResource findByPosition(int position) {
        return taxonomies.get(position);
    }
}
