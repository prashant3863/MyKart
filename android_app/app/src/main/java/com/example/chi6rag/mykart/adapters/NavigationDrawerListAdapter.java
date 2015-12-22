package com.example.chi6rag.mykart.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.models.CategoriesResource;
import com.example.chi6rag.mykart.models.CategoryResource;
import com.example.chi6rag.mykart.models.ProductCategory;

import java.util.List;

public class NavigationDrawerListAdapter extends BaseExpandableListAdapter {
    private CategoriesResource mCategoriesResource;
    private LayoutInflater mLayoutInflator;

    public NavigationDrawerListAdapter(Activity context) {
        this.mCategoriesResource = new CategoriesResource();
        this.mLayoutInflator = context.getLayoutInflater();
    }

    @Override
    public int getGroupCount() {
        return this.mCategoriesResource.length();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getGroup(groupPosition).productCategories().size();
    }

    @Override
    public CategoryResource getGroup(int groupPosition) {
        return this.mCategoriesResource.get(groupPosition);
    }

    @Override
    public ProductCategory getChild(int groupPosition, int childPosition) {
        CategoryResource categoryResource = getGroup(groupPosition);
        List<ProductCategory> productCategories = categoryResource.productCategories();
        return productCategories.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return getGroup(groupPosition).id;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getChild(groupPosition, childPosition).id;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CategoryResource categoryResource = getGroup(groupPosition);
        TextView view;
        if (convertView == null) {
            view = (TextView) mLayoutInflator.inflate(R.layout.navigation_drawer_group_element, parent, false);
        } else {
            view = (TextView) convertView;
        }
        view.setText(categoryResource.name);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ProductCategory productCategory = getChild(groupPosition, childPosition);
        TextView view;
        if (convertView == null) {
            view = (TextView) mLayoutInflator.inflate(R.layout.navigation_drawer_child_element, parent, false);
        } else {
            view = (TextView) convertView;
        }
        view.setText(productCategory.name);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

//
//    @Override
//    public int getCount() {
//        return mCategoriesResource.length();
//    }
//
//    @Override
//    public CategoryResource getItem(int position) {
//        CategoryResource category = mCategoriesResource.findByPosition(position);
//        return category;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        CategoryResource categoryResource = getItem(position);
//        View view;
//        if (convertView == null) {
//    view=mLayoutInflator.inflate(R.layout.navigation_drawer_list_element,parent,false);
//        } else {
//            view = convertView;
//        }
//        TextView navDrawerListElementText = (TextView) view
//                .findViewById(R.id.navigation_drawer_list_element_text);
//        navDrawerListElementText.setText(categoryResource.name);
//        return view;
//    }

    public CategoryResource findCategoryResourceByPosition(int position) {
        CategoryResource categoryResource = mCategoriesResource.findByPosition(position);
        return categoryResource;
    }

    public void populateCategories(CategoriesResource categoriesResource) {
        this.mCategoriesResource.addAll(categoriesResource);
    }
}
