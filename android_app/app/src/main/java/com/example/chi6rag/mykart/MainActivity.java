package com.example.chi6rag.mykart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.chi6rag.mykart.adapters.NavigationDrawerListAdapter;
import com.example.chi6rag.mykart.async_tasks.FetchCategoriesTask;
import com.example.chi6rag.mykart.models.*;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private LinearLayout mNavigationDrawer;
    private ListView mNavigationDrawerOptionsList;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    public static Product[] PRODUCTS = {
            new Product(1, "Skinny-Fit Big Pony Polo", "Ribbed polo collar\nTwo-button placket\nLong sleeves with ribbed cuffs", 14990, "Women",
                    new int[]{R.drawable.skinny_fit_big_pony_polo_1, R.drawable.skinny_fit_big_pony_polo_2}),
            new Product(2, "Tweed & Faux Leather Tank", "Ribbed polo collar\nTwo-button placket\nLong sleeves with ribbed cuffs", 14990, "Women",
                    new int[]{R.drawable.tweed_faux_leather_tank_1, R.drawable.tweed_faux_leather_tank_2, R.drawable.tweed_faux_leather_tank_3}),
            new Product(3, "Long-Sleeve Oxford Shirt", "Ribbed polo collar\nTwo-button placket\nLong sleeves with ribbed cuffs", 14990, "Women",
                    new int[]{R.drawable.long_sleeve_oxford_shirt}),
            new Product(4, "Crisscross-Back Maxi Dress", "Ribbed polo collar\nTwo-button placket\nLong sleeves with ribbed cuffs", 14990, "Women",
                    new int[]{R.drawable.crisscross_back_maxi_dress_1, R.drawable.crisscross_back_maxi_dress_2}),
            new Product(5, "Faux-Suede Trim Henley Dress", "Ribbed polo collar\nTwo-button placket\nLong sleeves with ribbed cuffs", 14990, "Women",
                    new int[]{R.drawable.faux_suede_trim_henley_dress_1, R.drawable.faux_suede_trim_henley_dress_2}),
            new Product(6, "Cap-Sleeve Cutout-Neckline Sheath", "Ribbed polo collar\nTwo-button placket\nLong sleeves with ribbed cuffs", 14990, "Women",
                    new int[]{R.drawable.cap_sleeve_cutout_neckline_sheath_1, R.drawable.cap_sleeve_cutout_neckline_sheath_2})
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        mNavigationDrawer = (LinearLayout) findViewById(R.id.navigation_drawer);
        mNavigationDrawerOptionsList = (ListView) findViewById(R.id.navigation_drawer_options);

        final NavigationDrawerListAdapter navigationDrawerListAdapter = new NavigationDrawerListAdapter(this);
        new FetchCategoriesTask(mNavigationDrawerOptionsList, navigationDrawerListAdapter).execute();

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

        mNavigationDrawerOptionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = navigationDrawerListAdapter.findCategoryByPosition(position);

                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                intent.putExtra("category", (Parcelable) category);
                startActivity(intent);
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
