package com.example.chi6rag.mykart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    public static String[] CATEGORIES = {
            "Men",
            "Women",
            "Kids"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView navigationDrawerOptionsList = (ListView) findViewById(R.id.navigation_drawer_options);
        NavigationDrawerListAdapter navigationDrawerListAdapter = new NavigationDrawerListAdapter(this);
        navigationDrawerOptionsList.setAdapter(navigationDrawerListAdapter);

    }
}
