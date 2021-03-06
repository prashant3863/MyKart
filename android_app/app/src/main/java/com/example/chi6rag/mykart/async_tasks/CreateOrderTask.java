package com.example.chi6rag.mykart.async_tasks;

import android.os.AsyncTask;

import com.example.chi6rag.mykart.models.Order;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateOrderTask extends AsyncTask<Void, Void, Order> {
    private static final String POST = "POST";
    private final StatusCallback[] mStatusCallbacks;

    public CreateOrderTask(StatusCallback<Order>... statusCallbacks) {
        this.mStatusCallbacks = statusCallbacks;
    }

    @Override
    protected Order doInBackground(Void... params) {
        try {
            URL url = new URL("http://mykartapp.herokuapp.com/api/orders");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(POST);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Order order = new Gson().fromJson(bufferedReader, Order.class);
            return order;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Order order) {
        for(StatusCallback statusCallback : this.mStatusCallbacks) {
            if (order != null) {
                statusCallback.onSuccess(order);
            } else {
                statusCallback.onFailure();
            }
        }
    }
}
