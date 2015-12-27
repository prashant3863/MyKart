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

    private void showNotConnectedToInternetAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setTitle("Network Error")
                .setMessage("No Internet Connection")
                .create()
                .show();
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
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_layout, productsFragment, TAG_ACTIVITY_MAIN_LAYOUT)
                    .commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_layout_right, new ProductDetailFragment(), TAG_ACTIVITY_MAIN_LAYOUT_RIGHT)
                    .commit();

            increaseWeightOfDetailFragmentContainer();
        }
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
            ProductDetailFragment fragment = (ProductDetailFragment) getSupportFragmentManager().findFragmentByTag(TAG_ACTIVITY_MAIN_LAYOUT_RIGHT);
            fragment.populate(product);
        }
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
}
