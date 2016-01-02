package com.example.chi6rag.mykart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.chi6rag.mykart.adapters.PaymentMethodListAdapter;
import com.example.chi6rag.mykart.models.Order;

public class PaymentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_fragment, container, false);
        Order order = getArguments().getParcelable(Order.TAG);

        ListView paymentMethodsList = ((ListView) view.findViewById(R.id.payment_methods_list));
        PaymentMethodListAdapter paymentMethodListAdapter =
                new PaymentMethodListAdapter(getContext(), order.paymentMethods());
        paymentMethodsList.setAdapter(paymentMethodListAdapter);

        return view;
    }
}
