package com.example.chi6rag.mykart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.chi6rag.mykart.models.Order;
import com.example.chi6rag.mykart.models.Product;

public class ProductActivity extends AppCompatActivity {
    private String host;
    private String port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Product product = getIntent().getParcelableExtra(Product.TAG);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Product.TAG, product);

        ProductDetailFragment productDetailFragment = new ProductDetailFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.product_container, productDetailFragment)
                .commit();

        productDetailFragment.populate(product);
    }

    private String fetchCurrentOrderNumber() {
        SharedPreferences orderSharedPreferences = getSharedPreferences(Order.TAG, MODE_PRIVATE);
        return orderSharedPreferences.getString(Order.CURRENT_NUMBER_KEY, null);
    }
}
