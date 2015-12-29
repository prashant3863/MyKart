package com.example.chi6rag.mykart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chi6rag.mykart.R;

public class CartLineItemQuantitySpinnerAdapter extends BaseAdapter {
    private final Context context;
    private final Integer defaultNumberOfSelections;
    private final Integer currentQuantity;

    public CartLineItemQuantitySpinnerAdapter(Context context,
                                              Integer currentQuantity,
                                              Integer defaultNumberOfSelections) {
        this.context = context;
        this.currentQuantity = currentQuantity;
        this.defaultNumberOfSelections = defaultNumberOfSelections;
    }

    @Override
    public int getCount() {
        return calculateMaxQuantity();
    }

    @Override
    public Integer getItem(int position) {
        return getQuantity(position);
    }

    public Integer positionForCurrentQuantity() {
        return currentQuantity - 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.cart_line_item_quantity_spinner_item, parent, false);
        } else {
            view = convertView;
        }
        TextView quantityText = (TextView) view.findViewById(R.id.cart_line_item_quantity_spinner_text);
        quantityText.setText(getItem(position) + "");
        return view;
    }

    private int getQuantity(int position) {
        return position + 1;
    }

    private int calculateMaxQuantity() {
        if (this.currentQuantity <= this.defaultNumberOfSelections) {
            return this.defaultNumberOfSelections;
        }
        return this.currentQuantity;
    }
}
