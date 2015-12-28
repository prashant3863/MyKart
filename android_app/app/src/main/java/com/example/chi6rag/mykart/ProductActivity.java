package com.example.chi6rag.mykart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.chi6rag.mykart.async_tasks.Callback;
import com.example.chi6rag.mykart.models.Cart;
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
        Order.getCurrentInstance(this, new Callback<Order>() {
            @Override
            public void onSuccess(Order fetchedOrder) {
                Cart cart = Cart.getInstance(ProductActivity.this, fetchedOrder);
                cart.addProduct(product);
            }

            @Override
            public void onFailure() {

            }
        });
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
            default:
        }
        return false;
    }
}
