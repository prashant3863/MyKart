package com.example.chi6rag.mykart.models;

import java.util.List;

public class PaymentMethods {
    private final List<PaymentMethod> paymentMethods;

    public PaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public int count() {
        return paymentMethods.size();
    }

    public PaymentMethod get(int position) {
        return this.paymentMethods.get(position);
    }
}
