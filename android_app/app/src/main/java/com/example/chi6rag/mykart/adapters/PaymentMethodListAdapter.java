package com.example.chi6rag.mykart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.models.PaymentMethod;
import com.example.chi6rag.mykart.models.PaymentMethods;

public class PaymentMethodListAdapter extends BaseAdapter {
    private final Context context;
    private final PaymentMethods paymentMethods;

    public PaymentMethodListAdapter(Context context, PaymentMethods paymentMethods) {
        this.context = context;
        this.paymentMethods = paymentMethods;
    }

    @Override
    public int getCount() {
        return paymentMethods.count();
    }

    @Override
    public PaymentMethod getItem(int position) {
        return paymentMethods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PaymentMethod paymentMethod = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.payment_method_list_item,
                    parent, false);
        } else {
            view = convertView;
        }
        TextView productMethodListItemText = ((TextView) view
                .findViewById(R.id.product_method_list_item_text));
        productMethodListItemText.setText(paymentMethod.toString());
        return view;
    }
}
