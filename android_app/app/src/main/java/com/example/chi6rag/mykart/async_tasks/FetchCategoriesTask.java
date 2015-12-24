package com.example.chi6rag.mykart.async_tasks;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.adapters.NavigationDrawerListAdapter;
import com.example.chi6rag.mykart.network.CategoriesResource;
import com.example.chi6rag.mykart.network.ConnectionDetector;
import com.example.chi6rag.mykart.utils.ViewFlipper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchCategoriesTask extends AsyncTask<Void, Void, CategoriesResource> {
    private static final String LOG_TAG = "error";
    private final String host;
    private final String port;
    private final String categoriesEndpoint;

    private final LinearLayout listContainer;
    private final ProgressBar progressBar;
    private final Context context;
    private NavigationDrawerListAdapter adapter;
    private ExpandableListView expandableList;
    private ConnectionDetector connectionDetector;

    public FetchCategoriesTask(Context context, LinearLayout listContainer, View progressBar,
                               NavigationDrawerListAdapter adapter) {
        this.context = context;
        Resources resources = context.getResources();

        host = resources.getString(R.string.host);
        port = resources.getString(R.string.port);
        categoriesEndpoint = host + port + resources.getString(R.string.taxonomies_path);

        this.progressBar = (ProgressBar) progressBar;
        this.adapter = adapter;
        this.listContainer = listContainer;
        this.expandableList = (ExpandableListView) this.listContainer.findViewById(R.id.navigation_drawer_options);
        this.connectionDetector = new ConnectionDetector(context);
    }

    @Override
    protected void onPreExecute() {
        progressBar.animate().start();
    }

    @Override
    protected CategoriesResource doInBackground(Void... params) {
        try {
            URL url = new URL(categoriesEndpoint);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            CategoriesResource categoriesResource = new Gson().fromJson(bufferedReader, CategoriesResource.class);
            return categoriesResource;
        } catch (IOException e) {
            Log.d(LOG_TAG, e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(CategoriesResource categoriesResource) {
        if (connectionDetector.isNotConnectedToInternet() || categoriesResource == null) {
            TextView textView = createErrorTextView();
            new ViewFlipper(context).replace(this.progressBar, textView);
            return;
        }
        adapter.populateCategories(categoriesResource);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        listContainer.setVisibility(LinearLayout.VISIBLE);
        expandAllGroups(expandableList);
    }

    @NonNull
    private TextView createErrorTextView() {
        TextView textView = new TextView(this.context);
        textView.setText("Cannot Fetch Categories");
        textView.setLayoutParams(this.progressBar.getLayoutParams());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        return textView;
    }

    private void expandAllGroups(ExpandableListView expandableList) {
        for (int groupPosition = 0; groupPosition < adapter.getGroupCount(); groupPosition++) {
            expandableList.expandGroup(groupPosition);
        }
    }
}
