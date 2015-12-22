package com.example.chi6rag.mykart.async_tasks;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.chi6rag.mykart.R;
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
    private final String HOST;
    private final String PORT;
    private final String CATEGORIES_ENDPOINT;
    private final String X_SPREE_TOKEN;
    private final String API_KEY;

    private final LinearLayout listContainer;
    private final ProgressBar progressBar;
    private NavigationDrawerListAdapter adapter;

    public FetchCategoriesTask(Context context, LinearLayout listContainer, View progressBar,
                               NavigationDrawerListAdapter adapter) {
        Resources resources = context.getResources();

        HOST = resources.getString(R.string.host);
        PORT = resources.getString(R.string.port);
        CATEGORIES_ENDPOINT = HOST + PORT + resources.getString(R.string.taxonomies_path);
        API_KEY = resources.getString(R.string.default_api_key);
        X_SPREE_TOKEN = resources.getString(R.string.x_spree_token);

        this.listContainer = listContainer;
        this.progressBar = (ProgressBar) progressBar;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        progressBar.animate().start();
    }

    @Override
    protected CategoriesResource doInBackground(Void... params) {
        try {
            URL url = new URL(CATEGORIES_ENDPOINT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
        adapter.notifyDataSetChanged();
        ((ViewGroup) progressBar.getParent()).removeView(progressBar);
        listContainer.setVisibility(LinearLayout.VISIBLE);
    }
}
