package com.example.chi6rag.mykart.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chi6rag.mykart.network.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoriesListAdapter extends BaseAdapter {
    private final LayoutInflater layoutInflator;
    ArrayList<ProductCategory> productCategories;

    public ProductCategoriesListAdapter(Activity context) {
        this.layoutInflator = context.getLayoutInflater();
        this.productCategories = new ArrayList<ProductCategory>();
    }

    @Override
    public int getCount() {
        return productCategories.size();
    }

    @Override
    public ProductCategory getItem(int position) {
        return this.productCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductCategory productCategory = getItem(position);
        View view;
        if (convertView == null) {
            view = this.layoutInflator.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }
        TextView productCategoryName = (TextView) view.findViewById(android.R.id.text1);
        productCategoryName.setText(productCategory.name);
        return view;
    }

    public void populateProductCategories(List<ProductCategory> productCategories) {
        this.productCategories.addAll(productCategories);
    }
}
