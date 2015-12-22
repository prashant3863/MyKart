package com.example.chi6rag.mykart;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.chi6rag.mykart.adapters.NavigationDrawerListAdapter;
import com.example.chi6rag.mykart.async_tasks.FetchCategoriesTask;
import com.example.chi6rag.mykart.models.CategoryResource;
import com.example.chi6rag.mykart.models.ProductCategory;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private LinearLayout mNavigationDrawer;
    private ExpandableListView mNavigationDrawerOptionsList;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        mNavigationDrawer = (LinearLayout) findViewById(R.id.navigation_drawer);
        mNavigationDrawerOptionsList = (ExpandableListView) findViewById(R.id.navigation_drawer_options);

        final NavigationDrawerListAdapter navigationDrawerListAdapter = new NavigationDrawerListAdapter(this);
        mNavigationDrawerOptionsList.setAdapter(navigationDrawerListAdapter);

        new FetchCategoriesTask(this,
                mNavigationDrawerOptionsList,
                findViewById(R.id.categories_progress_bar),
                navigationDrawerListAdapter)
                .execute();

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationDrawerOptionsList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ProductCategory productCategory = navigationDrawerListAdapter.getChild(groupPosition, childPosition);

                Bundle fragmentArguments = new Bundle();
                fragmentArguments.putParcelable(ProductCategory.TAG, productCategory);
                ProductsFragment productsFragment = new ProductsFragment();
                productsFragment.setArguments(fragmentArguments);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_layout, productsFragment)
                        .commit();
                mDrawerLayout.closeDrawer(mNavigationDrawer);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (hasClickedNavigationDrawerIcon(item)) {
            if (mDrawerLayout.isDrawerOpen(mNavigationDrawer)) {
                mDrawerLayout.closeDrawer(mNavigationDrawer);
            } else {
                mDrawerLayout.openDrawer(mNavigationDrawer);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    private boolean hasClickedNavigationDrawerIcon(MenuItem item) {
        return mActionBarDrawerToggle.onOptionsItemSelected(item);
    }
}
