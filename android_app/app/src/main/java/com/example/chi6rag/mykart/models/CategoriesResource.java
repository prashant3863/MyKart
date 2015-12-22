package com.example.chi6rag.mykart.models;

import java.util.ArrayList;
import java.util.List;

public class CategoriesResource {
    public List<CategoryResource> taxonomies;

    public CategoriesResource() {
        this.taxonomies = new ArrayList<CategoryResource>();
    }

    public int length() {
        return taxonomies.size();
    }

    public void addAll(CategoriesResource categoriesResource) {
        this.taxonomies.addAll(categoriesResource.taxonomies);
    }

    public CategoryResource get(int position) {
        return taxonomies.get(position);
    }
}
