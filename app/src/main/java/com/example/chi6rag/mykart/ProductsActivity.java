package com.example.chi6rag.mykart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    private static final String LOG_TAG = "log_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : MainActivity.PRODUCTS) {
            if (product.category.equals(category)) {
                products.add(product);
            }
        }

        ListView productsList = (ListView) findViewById(R.id.products_list);
        ProductListAdapter productListAdapter = new ProductListAdapter(this, products);
        productsList.setAdapter(productListAdapter);
    }
}
