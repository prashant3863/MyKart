package com.example.chi6rag.mykart.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ViewFlipper {
    private final Context context;

    public ViewFlipper(Context context) {
        this.context = context;
    }

    public void replace(View originalView, View finalView) {
        ViewGroup containerViewGroup = ((ViewGroup) originalView.getParent());
        int originalViewIndex = containerViewGroup.indexOfChild(originalView);
        containerViewGroup.removeView(originalView);
        containerViewGroup.addView(finalView, originalViewIndex);
    }
}
