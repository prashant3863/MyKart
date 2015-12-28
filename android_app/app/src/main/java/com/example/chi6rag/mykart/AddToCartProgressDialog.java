package com.example.chi6rag.mykart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;

import com.example.chi6rag.mykart.async_tasks.UIExecutor;
import com.example.chi6rag.mykart.models.LineItem;

public class AddToCartProgressDialog {
    private final Activity context;

    public AddToCartProgressDialog(Activity context) {
        this.context = context;
    }

    @NonNull
    public UIExecutor<LineItem> buildExecutor(final ProgressDialog progressDialog) {
        return new UIExecutor<LineItem>() {
            @Override
            public void onPreExecute() {
                progressDialog.show();
            }

            @Override
            public void onPostExecute(LineItem lineItem) {
                progressDialog.hide();
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setTitle("Success")
                        .setMessage("Added " + lineItem.variant.name + " to Cart")
                        .show();
            }
        };
    }

    @NonNull
    public ProgressDialog build() {
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Adding Product To Cart");
        return progressDialog;
    }
}
