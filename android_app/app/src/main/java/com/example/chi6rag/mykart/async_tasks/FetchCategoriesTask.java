package com.example.chi6rag.mykart.async_tasks;

import android.os.AsyncTask;
import android.widget.ListView;

import com.example.chi6rag.mykart.adapters.NavigationDrawerListAdapter;
import com.example.chi6rag.mykart.models.CategoriesResource;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchCategoriesTask extends AsyncTask<Void, Void, CategoriesResource> {
    private static final String X_SPREE_TOKEN = "X-Spree-Token";
    private static final String API_KEY = "d7e0b9aac23ee67b4001bf9b330c8c53aab3db4501d34f98";
    final String HOST = "http://192.168.1.36";
    final String PORT = ":3000/";
    final String CATEGORIES_URL = HOST + PORT + "/api/taxonomies/";

    private final ListView list;
    private NavigationDrawerListAdapter adapter;

    public FetchCategoriesTask(ListView list, NavigationDrawerListAdapter adapter) {
        this.list = list;
        this.adapter = adapter;
    }

    @Override
    protected CategoriesResource doInBackground(Void... params) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(CATEGORIES_URL).openConnection();
            httpURLConnection.setRequestProperty(X_SPREE_TOKEN, API_KEY);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            CategoriesResource categoriesResource = new Gson().fromJson(bufferedReader, CategoriesResource.class);
            return categoriesResource;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(CategoriesResource categoriesResource) {
        adapter.populateCategories(categoriesResource);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}