package com.example.chi6rag.mykart.async_tasks;

import android.os.AsyncTask;

import com.example.chi6rag.mykart.models.Order;
import com.example.chi6rag.mykart.network.ErrorsResource;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdvanceOrderStateTask extends AsyncTask<Void, Void, Object> {
    private static final String PUT = "PUT";
    private static final String ERROR_RANGE = "4.*";
    private static final String SUCCESS_RANGE = "2.*";

    private final Order order;
    private final UIExecutor<Object> uiExecutor;
    private final ExtensibleStatusCallback<Object> extensibleStatusCallback;

    public AdvanceOrderStateTask(Order order,
                                 UIExecutor<Object> uiExecutor,
                                 ExtensibleStatusCallback<Object> statusCallback) {
        this.order = order;
        this.uiExecutor = uiExecutor;
        this.extensibleStatusCallback = statusCallback;
    }

    @Override
    protected void onPreExecute() {
        this.uiExecutor.onPreExecute();
    }

    @Override
    protected Object doInBackground(Void... params) {
        try {
            URL url = new URL(buildUrlString());
            HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            connection.setRequestMethod(PUT);

            String responseCodeString = String.valueOf(connection.getResponseCode());
            if (responseCodeString.matches(SUCCESS_RANGE)) {
                Order order = buildOrder(connection);
                return order;
            } else if (responseCodeString.matches(ERROR_RANGE)) {
                ErrorsResource errorResource = buildErrorResource(connection);
                return errorResource;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object object) {
        this.uiExecutor.onPostExecute(object);
        if (object instanceof Order) {
            this.extensibleStatusCallback.onSuccess(object);
        } else if (object instanceof ErrorsResource) {
            this.extensibleStatusCallback.onFailure(object);
        }
    }

    private ErrorsResource buildErrorResource(HttpURLConnection connection) {
        InputStream inputStream = connection.getErrorStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return new Gson().fromJson(bufferedReader, ErrorsResource.class);
    }

    private Order buildOrder(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return new Gson().fromJson(bufferedReader, Order.class);
    }

    private String buildUrlString() {
        return "http://mykartapp.herokuapp.com/api/checkouts/" + this.order.number +
                "/next?order_token=" + this.order.token;
    }
}
