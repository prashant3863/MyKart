package com.example.chi6rag.mykart;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductListAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflator;
    private ArrayList<Product> products;

    public ProductListAdapter(Activity context, ArrayList<Product> products) {
        this.mLayoutInflator = context.getLayoutInflater();
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);
        View view;
        if(convertView == null) {
            view = mLayoutInflator.inflate(R.layout.product_list_item, parent, false);
        }
        else {
            view = convertView;
        }
        TextView productListItemText = (TextView) view.findViewById(R.id.product_list_item_text);
        productListItemText.setText(product.name);
        return view;
    }
}

