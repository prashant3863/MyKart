package com.example.chi6rag.mykart.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.chi6rag.mykart.models.Cart;
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
    private final Cart cart;

    public AddProductToCartTask(Context context, Product product, Cart cart) {
        this.product = product;
        this.cart = cart;
    }

    @Override
    protected LineItem doInBackground(Void... params) {
        String urlString = buildUrlString(cart.orderNumber, product.id, cart.orderToken);
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
        cart.addLineItem(lineItem);
        Log.d("chi6rag", "Added line item to cart");
    }

    private String buildUrlString(String orderNumber, Integer productId, String orderToken) {
        return "http://mykartapp.herokuapp.com/api/orders/" + orderNumber +
                "/line_items?line_item[variant_id]=" + productId +
                "&line_item[quantity]=1&order_token=" + orderToken;
    }
}
