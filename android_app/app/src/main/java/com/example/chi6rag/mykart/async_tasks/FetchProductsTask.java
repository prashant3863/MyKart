package com.example.chi6rag.mykart.async_tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.chi6rag.mykart.adapters.NavigationDrawerListAdapter;
import com.example.chi6rag.mykart.models.CategoriesResource;
import com.example.chi6rag.mykart.models.ProductsResource;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchProductsTask extends AsyncTask<Integer, Void, ProductsResource> {
    private static final String X_SPREE_TOKEN = "X-Spree-Token";
    private static final String API_KEY = "d7e0b9aac23ee67b4001bf9b330c8c53aab3db4501d34f98";
    private static final String ID_KEY = "?id=";
    final String HOST = "http://192.168.1.106";
    final String PORT = ":3000/";
    final String PRODUCTS_ENDPOINT = HOST + PORT + "/api/taxons/products";

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
}
