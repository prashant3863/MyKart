package com.example.chi6rag.mykart;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NavigationDrawerListAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflator;
    private Activity mContext;

    public NavigationDrawerListAdapter(Activity context) {
        this.mContext = context;
        this.mLayoutInflator = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return MainActivity.CATEGORIES.length;
    }

    @Override
    public String getItem(int position) {
        return MainActivity.CATEGORIES[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String category = getItem(position);
        View view;
        if(convertView == null) {
            view = mLayoutInflator.inflate(R.layout.navigation_drawer_list_element, parent, false);
        }
        else {
            view = convertView;
        }
        TextView navDrawerListElementText = (TextView) view
                .findViewById(R.id.navigation_drawer_list_element_text);
        navDrawerListElementText.setText(category);
        return view;
    }
}
