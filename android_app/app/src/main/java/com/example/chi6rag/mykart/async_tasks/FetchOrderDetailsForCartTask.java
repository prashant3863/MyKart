package com.example.chi6rag.mykart.async_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.chi6rag.mykart.models.Order;
import com.example.chi6rag.mykart.network.ConnectionDetector;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchOrderDetailsForCartTask extends AsyncTask<Void, Void, Order> {
    private final Order order;
    private final StatusCallback<Order> mOrderStatusCallback;
    private final ProgressBar progressBar;
    private Context context;
    private ConnectionDetector connectionDetector;

    public FetchOrderDetailsForCartTask(Context context, Order order, ProgressBar progressBar,
                                        StatusCallback<Order> orderStatusCallback) {
        this.context = context;
        this.order = order;
        this.progressBar = progressBar;
        this.mOrderStatusCallback = orderStatusCallback;
        this.connectionDetector = new ConnectionDetector(this.context);
    }

    @Override
    protected void onPreExecute() {
        this.progressBar.animate().start();
    }

    @Override
    protected Order doInBackground(Void... params) {
        if (connectionDetector.isNotConnectedToInternet()) {
            return null;
        }
        try {
            URL url = new URL(buildURLString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
        if (order.isValid() && (order.lineItems != null)) {
            cancelProgressBarAnimation();
            removeProgressBar();
            this.mOrderStatusCallback.onSuccess(order);
        } else {
            this.mOrderStatusCallback.onFailure();
        }
    }

    private void removeProgressBar() {
        ((ViewGroup) this.progressBar.getParent()).removeView(progressBar);
    }

    private void cancelProgressBarAnimation() {
        this.progressBar.animate().cancel();
    }

    private String buildURLString() {
        return "http://mykartapp.herokuapp.com/api/orders/" + this.order.number + "?order_token=" + this.order.token;
    }
}
