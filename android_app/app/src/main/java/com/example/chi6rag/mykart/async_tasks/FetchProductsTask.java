package com.example.chi6rag.mykart.async_tasks;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.adapters.ProductListAdapter;
import com.example.chi6rag.mykart.models.ProductsResource;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchProductsTask extends AsyncTask<Integer, Void, ProductsResource> {
    private final String HOST;
    private final String PORT;
    private final String PRODUCTS_ENDPOINT;
    private final String X_SPREE_TOKEN;
    private final String API_KEY;
    private final String ID_KEY;

    private ProductListAdapter adapter;

    public FetchProductsTask(Context context, ProductListAdapter adapter) {
        Resources resources = context.getResources();

        HOST = resources.getString(R.string.host);
        PORT = resources.getString(R.string.port);
        PRODUCTS_ENDPOINT = HOST + PORT + resources.getString(R.string.products_path);
        API_KEY = resources.getString(R.string.default_api_key);
        X_SPREE_TOKEN = resources.getString(R.string.x_spree_token);
        ID_KEY = resources.getString(R.string.id_key);

        this.adapter = adapter;
    }

    @Override
    protected ProductsResource doInBackground(Integer... productCategoryId) {
        try {
            URL url = new URL(PRODUCTS_ENDPOINT + ID_KEY + productCategoryId[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty(X_SPREE_TOKEN, API_KEY);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            ProductsResource productsResource = new Gson().fromJson(bufferedReader, ProductsResource.class);
            return productsResource;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ProductsResource productsResource) {
        adapter.updateProducts(productsResource);
        adapter.notifyDataSetChanged();
    }
}
