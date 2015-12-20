package com.example.chi6rag.mykart.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.models.CategoriesResource;
import com.example.chi6rag.mykart.models.CategoryResource;

public class NavigationDrawerListAdapter extends BaseAdapter {
    private CategoriesResource mCategoriesResource;
    private LayoutInflater mLayoutInflator;

    public NavigationDrawerListAdapter(Activity context) {
        this.mCategoriesResource = new CategoriesResource();
        this.mLayoutInflator = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return mCategoriesResource.length();
    }

    @Override
    public CategoryResource getItem(int position) {
        CategoryResource category = mCategoriesResource.findByPosition(position);
        return category;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryResource categoryResource = getItem(position);
        View view;
        if (convertView == null) {
            view = mLayoutInflator.inflate(R.layout.navigation_drawer_list_element, parent, false);
        } else {
            view = convertView;
        }
        TextView navDrawerListElementText = (TextView) view
                .findViewById(R.id.navigation_drawer_list_element_text);
        navDrawerListElementText.setText(categoryResource.name);
        return view;
    }

    public CategoryResource findCategoryResourceByPosition(int position) {
        CategoryResource categoryResource = mCategoriesResource.findByPosition(position);
        return categoryResource;
    }

    public void populateCategories(CategoriesResource categoriesResource) {
        this.mCategoriesResource.addAll(categoriesResource);
    }
}
