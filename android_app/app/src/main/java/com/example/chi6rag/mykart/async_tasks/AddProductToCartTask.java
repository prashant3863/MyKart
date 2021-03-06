package com.example.chi6rag.mykart.async_tasks;

import android.os.AsyncTask;

import com.example.chi6rag.mykart.models.LineItem;
import com.example.chi6rag.mykart.models.Product;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddProductToCartTask extends AsyncTask<Void, Void, LineItem> {
    private static final String POST = "POST";
    private final Product product;
    private final String orderNumber;
    private final String orderToken;
    private final StatusCallback mStatusCallback;
    private final UIExecutor<LineItem> uiExecutor;

    public AddProductToCartTask(String orderNumber,
                                String orderToken,
                                Product product,
                                UIExecutor<LineItem> uiExecutor,
                                StatusCallback<LineItem> statusCallback) {
        this.orderNumber = orderNumber;
        this.orderToken = orderToken;
        this.product = product;
        this.uiExecutor = uiExecutor;
        this.mStatusCallback = statusCallback;
    }

    @Override
    protected void onPreExecute() {
        uiExecutor.onPreExecute();
    }

    @Override
    protected LineItem doInBackground(Void... params) {
        String urlString = buildUrlString(this.orderNumber, product.id, this.orderToken);
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(POST);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            LineItem lineItem = new Gson().fromJson(bufferedReader, LineItem.class);
            return lineItem;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(LineItem lineItem) {
        if (lineItem != null) {
            uiExecutor.onPostExecute(lineItem);
            this.mStatusCallback.onSuccess(lineItem);
        } else {
            this.mStatusCallback.onFailure();
        }
    }

    private String buildUrlString(String orderNumber, Integer productId, String orderToken) {
        return "http://mykartapp.herokuapp.com/api/orders/" + orderNumber +
                "/line_items?line_item[variant_id]=" + productId +
                "&line_item[quantity]=1&order_token=" + orderToken;
    }
}
