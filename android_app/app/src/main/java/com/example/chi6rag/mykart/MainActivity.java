package com.example.chi6rag.mykart;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.chi6rag.mykart.adapters.NavigationDrawerListAdapter;
import com.example.chi6rag.mykart.async_tasks.Callback;
import com.example.chi6rag.mykart.async_tasks.FetchCategoriesTask;
import com.example.chi6rag.mykart.models.Cart;
import com.example.chi6rag.mykart.models.Order;
import com.example.chi6rag.mykart.models.Product;
import com.example.chi6rag.mykart.network.CategoryResource;
import com.example.chi6rag.mykart.network.ConnectionDetector;
import com.example.chi6rag.mykart.network.ProductCategory;

public class MainActivity extends AppCompatActivity implements
        LandingFragment.OnLandingScreenCategoryClickListener,
        ProductCategoriesFragment.OnProductCategoryClickListener,
        ProductsFragment.OnProductClickListener,
        ProductDetailFragment.OnInteractionListener {

    public static final String TAG_ACTIVITY_MAIN_LAYOUT_RIGHT = "tag_activity_main_layout_right";
    public static final String TAG_ACTIVITY_MAIN_LAYOUT = "tag_activity_main_layout";
    private DrawerLayout mDrawerLayout;
    private LinearLayout mNavigationDrawer;
    private ExpandableListView mNavigationDrawerOptionsList;
    private LinearLayout mNavigationDrawerListContainer;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.bringToFront();

        showLandingScreen();

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

                if (isPortraitMode()) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activity_main_layout, productsFragment)
                            .commit();
                } else {
                    handleProductCategoryClickForLandscapeMode(productsFragment);
                    increaseWeightOfDetailFragmentContainer();
                }

                mDrawerLayout.closeDrawer(mNavigationDrawer);
                return true;
            }
        });
    }

    private void showLandingScreen() {
        LandingFragment landingFragment = new LandingFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, landingFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
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
        switch (item.getItemId()) {
            case R.id.action_cart:
                handleCartActionItemClick();
            case R.id.action_home:
                showLandingScreen();
        }
        return false;
    }

    private void handleCartActionItemClick() {
        Order.getCurrentInstance(this, new Callback<Order>() {
            @Override
            public void onSuccess(Order fetchedOrder) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.putExtra(Order.TAG, fetchedOrder);
                startActivity(intent);
            }

            @Override
            public void onFailure() {
                Log.d("chi6rag", "show internet exception alert");
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onLandingScreenCategoryClick(String gender) {
        ConnectionDetector connectionDetector = new ConnectionDetector(this);
        if (connectionDetector.isNotConnectedToInternet()) {
            showNotConnectedToInternetAlertDialog();
            return;
        }
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

        if (isPortraitMode()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_layout, productsFragment)
                    .commit();
        } else {
            handleProductCategoryClickForLandscapeMode(productsFragment);
            increaseWeightOfDetailFragmentContainer();
        }
    }

    @Override
    public void onProductClick(View view, Product product) {
        if (isPortraitMode()) {
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra(Product.TAG, product);

            if (isDeviceVersionAbove21()) {
                startActivityWithAnimation(view, intent);
            } else {
                startActivity(intent);
            }
        } else {
            ProductDetailFragment fragment = (ProductDetailFragment) getSupportFragmentManager()
                    .findFragmentByTag(TAG_ACTIVITY_MAIN_LAYOUT_RIGHT);
            if (fragment != null)
                fragment.populate(product);
        }
    }

    @Override
    public void onAddToCartButtonClick(final Product product) {
        Order.getCurrentInstance(this, new Callback<Order>() {
            @Override
            public void onSuccess(Order fetchedOrder) {
                Cart cart = Cart.getInstance(MainActivity.this, fetchedOrder);
                cart.addProduct(product);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void handleProductCategoryClickForLandscapeMode(ProductsFragment productsFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout, productsFragment, TAG_ACTIVITY_MAIN_LAYOUT)
                .commit();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_layout_right, new ProductDetailFragment(), TAG_ACTIVITY_MAIN_LAYOUT_RIGHT)
                .commit();
    }

    private void increaseWeightOfDetailFragmentContainer() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT, 1.5f);
        ((FrameLayout) findViewById(R.id.activity_main_layout_right)).setLayoutParams(layoutParams);
    }

    private boolean isPortraitMode() {
        return findViewById(R.id.activity_main_layout_right) == null;
    }

    private boolean isDeviceVersionAbove21() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void startActivityWithAnimation(View view, Intent intent) {
        View productListItemImage = view.findViewById(R.id.product_list_item_image);
        View productListItemName = view.findViewById(R.id.product_list_item_name);

        Pair<View, String> imageSharedElement = Pair.create(productListItemImage, "product_list_item_image");
        Pair<View, String> nameSharedElement = Pair.create(productListItemName, "product_list_item_name");

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                imageSharedElement, nameSharedElement);
        startActivity(intent, options.toBundle());
    }

    private void showNotConnectedToInternetAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setTitle("Network Error")
                .setMessage("No Internet Connection")
                .create()
                .show();
    }

    private boolean hasClickedNavigationDrawerIcon(MenuItem item) {
        return mActionBarDrawerToggle.onOptionsItemSelected(item);
    }
}
