package com.example.chi6rag.mykart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    private static final String LOG_TAG = "chi6rag";

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

        RecyclerView productsList = (RecyclerView) findViewById(R.id.products_list);
        productsList.setLayoutManager(new GridLayoutManager(this, 2));

        ProductListAdapter productListAdapter = new ProductListAdapter(products);
        productsList.setAdapter(productListAdapter);

        productsList.addOnItemTouchListener(new ProductsListTouchListener(this,
                new ProductsListTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String idTag = String.valueOf(view.getTag(R.id.product_id_tag));
                Log.d(LOG_TAG, idTag);
            }
        }));
    }
}
