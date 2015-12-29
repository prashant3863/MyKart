package com.example.chi6rag.mykart.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.models.LineItem;
import com.example.chi6rag.mykart.models.Variant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LineItemListAdapter extends BaseAdapter {

    private static final String QUANTITY = "Quantity: ";
    private static final String PRICE = "Price Per Item: ";
    private static final Integer FIVE = 5;
    private final Context context;
    private final List<LineItem> lineItems;
    private final String host;
    private final Object port;

    public LineItemListAdapter(Context context, List<LineItem> lineItems) {
        this.context = context;
        this.lineItems = lineItems;
        Resources resources = context.getResources();
        this.host = resources.getString(R.string.host);
        this.port = resources.getString(R.string.port);
    }

    @Override
    public int getCount() {
        return lineItems.size();
    }

    @Override
    public LineItem getItem(int position) {
        return lineItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LineItem lineItem = getItem(position);
        Variant variant = lineItem.variant;
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.cart_line_item, parent, false);
        } else {
            view = convertView;
        }
        populateDataInView(lineItem, variant, view);
        return view;
    }

    private void populateDataInView(LineItem lineItem, Variant variant, View view) {
        ImageView cartLineItemImage = ((ImageView) view.findViewById(R.id.cart_line_item_image));
        TextView cartLineItemName = (TextView) view.findViewById(R.id.cart_line_item_name);
        TextView cartLineItemPrice = ((TextView) view.findViewById(R.id.cart_line_item_price));
        Spinner cartLineItemQuantitySpinner = (Spinner) view.findViewById(R.id
                .cart_line_item_quantity_spinner);

        CartLineItemQuantitySpinnerAdapter adapter =
                new CartLineItemQuantitySpinnerAdapter(this.context, lineItem.quantity, FIVE);

        Picasso.with(this.context)
                .load(host + port + variant.images.get(0).smallUrl)
                .placeholder(R.drawable.line_item_image_placeholder)
                .into(cartLineItemImage);

        cartLineItemName.setText(variant.name);
        cartLineItemPrice.setText(PRICE + variant.displayPrice);
        cartLineItemQuantitySpinner.setAdapter(adapter);
        cartLineItemQuantitySpinner.setSelection(adapter.positionForCurrentQuantity());
    }
}
