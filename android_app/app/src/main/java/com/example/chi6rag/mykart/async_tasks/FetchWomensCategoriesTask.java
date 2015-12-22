package com.example.chi6rag.mykart.async_tasks;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.adapters.ProductCategoriesListAdapter;
import com.example.chi6rag.mykart.models.CategoriesResource;
import com.example.chi6rag.mykart.models.ProductCategory;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FetchWomensCategoriesTask extends AsyncTask<Void, Void, CategoriesResource> {
    private final String HOST;
    private final String PORT;
    private final String WOMENS_CATEGORIES_ENDPOINT;
    private final String X_SPREE_TOKEN;
    private final String API_KEY;
    private final String WOMENS_CATEGORIES_PATH;
    private final ProductCategoriesListAdapter adapter;
    private final ListView list;
    private final RelativeLayout container;
    private final ProgressBar progressBar;

    public FetchWomensCategoriesTask(Context context, ProductCategoriesListAdapter adapter,
                                     RelativeLayout container, ListView list) {
        Resources resources = context.getResources();
        this.adapter = adapter;
        this.container = container;
        this.list = list;

        HOST = resources.getString(R.string.host);
        PORT = resources.getString(R.string.port);
        WOMENS_CATEGORIES_PATH = context.getString(R.string.womens_categories_path);
        WOMENS_CATEGORIES_ENDPOINT = HOST + PORT + WOMENS_CATEGORIES_PATH;
        API_KEY = resources.getString(R.string.default_api_key);
        X_SPREE_TOKEN = resources.getString(R.string.x_spree_token);
        this.progressBar = (ProgressBar) this.container.findViewById(R.id.product_categories_progress_bar);
    }

    @Override
    protected void onPreExecute() {
        this.progressBar.animate().start();
    }

    @Override
    protected CategoriesResource doInBackground(Void... params) {
        try {
            URL url = new URL(WOMENS_CATEGORIES_ENDPOINT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty(X_SPREE_TOKEN, API_KEY);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            CategoriesResource categoriesResource = new Gson().fromJson(bufferedReader,
                    CategoriesResource.class);
            return categoriesResource;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(CategoriesResource categoriesResource) {
        List<ProductCategory> productCategories = categoriesResource.get(0).productCategories();
        adapter.populateProductCategories(productCategories);
        adapter.notifyDataSetChanged();
        this.progressBar.animate().cancel();
        removeContainer();
        this.list.setVisibility(ListView.VISIBLE);
    }

    private void removeContainer() {
        ((ViewGroup) this.container.getParent()).removeView(this.container);
    }
}
