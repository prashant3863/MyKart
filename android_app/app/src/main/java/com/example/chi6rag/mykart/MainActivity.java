package com.example.chi6rag.mykart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.example.chi6rag.mykart.adapters.NavigationDrawerListAdapter;
import com.example.chi6rag.mykart.async_tasks.FetchCategoriesTask;
import com.example.chi6rag.mykart.models.CategoryResource;
import com.example.chi6rag.mykart.models.Product;
import com.example.chi6rag.mykart.models.ProductCategory;

public class MainActivity extends AppCompatActivity implements
        LandingFragment.OnLandingScreenCategoryClickListener,
        ProductCategoriesFragment.OnProductCategoryClickListener,
        ProductsFragment.OnProductClickListener {
    private DrawerLayout mDrawerLayout;
    private LinearLayout mNavigationDrawer;
    private ExpandableListView mNavigationDrawerOptionsList;
    private LinearLayout mNavigationDrawerListContainer;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LandingFragment landingFragment = new LandingFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, landingFragment)
                .commit();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        mNavigationDrawer = (LinearLayout) findViewById(R.id.navigation_drawer);
        mNavigationDrawerOptionsList = (ExpandableListView) findViewById(R.id.navigation_drawer_options);
        mNavigationDrawerListContainer = (LinearLayout) findViewById(R.id.navigation_drawer_list_container);

        final NavigationDrawerListAdapter navigationDrawerListAdapter = new NavigationDrawerListAdapter(this);
        mNavigationDrawerOptionsList.setAdapter(navigationDrawerListAdapter);
        new FetchCategoriesTask(this,
                mNavigationDrawerListContainer,
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
// TODO: Amir - 23/12/15 - try adding presenter unit test for interaction

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
            // TODO: Amir - 23/12/15 - drawer bar framework should do the toggle
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

    @Override
    public void onLandingScreenCategoryClick(String gender) {
        Bundle fragmentArguments = new Bundle();
        fragmentArguments.putString(CategoryResource.TAG, gender);

        ProductCategoriesFragment productCategoriesFragment = new ProductCategoriesFragment();
        productCategoriesFragment.setArguments(fragmentArguments);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, productCategoriesFragment)
                .commit();
    }

    @Override
    public void onProductCategoryClick(ProductCategory productCategory) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ProductCategory.TAG, productCategory);

        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, productsFragment)
                .commit();
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra(Product.TAG, product);
        startActivity(intent);
    }
}
