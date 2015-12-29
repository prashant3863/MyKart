package com.example.chi6rag.mykart.async_tasks;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.chi6rag.mykart.ProductsListTouchListener;
import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.adapters.ProductListAdapter;
import com.example.chi6rag.mykart.models.Product;
import com.example.chi6rag.mykart.network.ProductsResource;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchProductsTask extends AsyncTask<Integer, Void, ProductsResource> {
    private final String host;
    private final String port;
    private final String productsEndpoint;
    private final String idKey;
    private final RelativeLayout progressContainer;
    private final ProgressBar progressBar;
    private final RecyclerView productsList;
    private final Context context;

    private ProductListAdapter adapter;

    public FetchProductsTask(Context context, ProductListAdapter adapter, RelativeLayout progressContainer,
                             View productsList) {
        this.context = context;
        Resources resources = context.getResources();

        host = resources.getString(R.string.host);
        port = resources.getString(R.string.port);
        productsEndpoint = host + port + resources.getString(R.string.products_path);
        idKey = resources.getString(R.string.id_key);

        this.adapter = adapter;
        this.progressContainer = progressContainer;
        this.progressBar = (ProgressBar) progressContainer.findViewById(R.id.products_progress_bar);
        this.productsList = (RecyclerView) productsList;
    }

    @Override
    protected void onPreExecute() {
        progressBar.animate().start();
    }

    @Override
    protected ProductsResource doInBackground(Integer... productCategoryId) {
        try {
            URL url = new URL(productsEndpoint + idKey + productCategoryId[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
        ((ViewGroup) this.progressContainer.getParent()).removeView(this.progressContainer);
        this.productsList.setVisibility(RecyclerView.VISIBLE);
    }
}
