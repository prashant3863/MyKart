package com.example.chi6rag.mykart.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.models.Categories;
import com.example.chi6rag.mykart.models.Category;

public class NavigationDrawerListAdapter extends BaseAdapter {
    private Categories categories;
    private LayoutInflater mLayoutInflator;

    public NavigationDrawerListAdapter(Activity context) {
        this.mLayoutInflator = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return categories.length();
    }

    @Override
    public Category getItem(int position) {
        Category category = categories.findByPosition(position);
        return category;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = getItem(position);
        View view;
        if (convertView == null) {
            view = mLayoutInflator.inflate(R.layout.navigation_drawer_list_element, parent, false);
        } else {
            view = convertView;
        }
        TextView navDrawerListElementText = (TextView) view
                .findViewById(R.id.navigation_drawer_list_element_text);
        navDrawerListElementText.setText(category.name);
        return view;
    }

    public Category findCategoryByPosition(int position) {
        Category category = categories.findByPosition(position);
        return category;
    }

    public void populateCategories(Categories categories) {
        this.categories = categories;
    }
}
