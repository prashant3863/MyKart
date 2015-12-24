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
import com.example.chi6rag.mykart.network.CategoriesResource;
import com.example.chi6rag.mykart.network.CategoryResource;
import com.example.chi6rag.mykart.network.ProductCategory;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FetchCategoriesBasedOnGenderTask extends AsyncTask<Void, Void, CategoriesResource> {
    private final String host;
    private final String port;
    private final String categorieEndpoint;
    private final String categoriesPath;
    private final ProductCategoriesListAdapter adapter;
    private final ListView list;
    private final RelativeLayout container;
    private final ProgressBar progressBar;
    private final Context context;

    public FetchCategoriesBasedOnGenderTask(Context context, String gender, ProductCategoriesListAdapter adapter,
                                            RelativeLayout container, ListView list) {
        this.context = context;
        Resources resources = context.getResources();
        this.adapter = adapter;
        this.container = container;
        this.list = list;

        host = resources.getString(R.string.host);
        port = resources.getString(R.string.port);
        categoriesPath = fetchCategoriesPathBasedOnGender(gender);
        categorieEndpoint = host + port + categoriesPath;
        this.progressBar = (ProgressBar) this.container.findViewById(R.id.product_categories_progress_bar);
    }

    private String fetchCategoriesPathBasedOnGender(String gender) {
        if (gender.equals(CategoryResource.MEN)) {
            return context.getString(R.string.mens_categories_path);
        } else if (gender.equals(CategoryResource.WOMEN)) {
            return context.getString(R.string.womens_categories_path);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        this.progressBar.animate().start();
    }

    @Override
    protected CategoriesResource doInBackground(Void... params) {
        try {
            URL url = new URL(categorieEndpoint);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
