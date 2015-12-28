package com.example.chi6rag.mykart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.chi6rag.mykart.async_tasks.Callback;
import com.example.chi6rag.mykart.async_tasks.UIExecutor;
import com.example.chi6rag.mykart.models.Cart;
import com.example.chi6rag.mykart.models.LineItem;
import com.example.chi6rag.mykart.models.Order;
import com.example.chi6rag.mykart.models.Product;

public class ProductActivity extends AppCompatActivity implements
        ProductDetailFragment.OnInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setupActionBar();

        Product product = getIntent().getParcelableExtra(Product.TAG);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Product.TAG, product);

        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        productDetailFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.product_container, productDetailFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onAddToCartButtonClick(final Product product) {
        final ProgressDialog progressDialog = buildAddingProductToCartProgressDialog();
        final UIExecutor<LineItem> uiExecutor = buildProgressDialogExecutor(progressDialog);

        Order.getCurrentInstance(this, new Callback<Order>() {
            @Override
            public void onSuccess(Order fetchedOrder) {
                Cart cart = Cart.getInstance(ProductActivity.this, fetchedOrder);
                cart.addProduct(product, uiExecutor);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @NonNull
    private UIExecutor<LineItem> buildProgressDialogExecutor(final ProgressDialog progressDialog) {
        return new UIExecutor<LineItem>() {
            @Override
            public void onPreExecute() {
                progressDialog.show();
            }

            @Override
            public void onPostExecute(LineItem lineItem) {
                progressDialog.hide();
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ProductActivity.this);
                alertBuilder.setTitle("Success")
                        .setMessage("Added " + lineItem.variant.name + " to Cart")
                        .show();
            }
        };
    }

    @NonNull
    private ProgressDialog buildAddingProductToCartProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Adding Product To Cart");
        return progressDialog;
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.bringToFront();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
            case R.id.action_home:
                finish();
            case R.id.action_cart:
                handleCartActionItemClick();
        }
        return false;
    }

    private void handleCartActionItemClick() {
        Order.getCurrentInstance(this, new Callback<Order>() {
            @Override
            public void onSuccess(Order fetchedOrder) {
                Intent intent = new Intent(ProductActivity.this, CartActivity.class);
                intent.putExtra(Order.TAG, fetchedOrder);
                startActivity(intent);
            }

            @Override
            public void onFailure() {
                Log.d("chi6rag", "show internet exception alert");
            }
        });
    }
}
