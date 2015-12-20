package com.example.chi6rag.mykart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.chi6rag.mykart.adapters.ProductListAdapter;
import com.example.chi6rag.mykart.async_tasks.FetchProductsTask;
import com.example.chi6rag.mykart.models.CategoryResource;
import com.example.chi6rag.mykart.models.Product;
import com.example.chi6rag.mykart.models.ProductCategory;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Intent intent = getIntent();
        ProductCategory productCategory = intent.getParcelableExtra(ProductCategory.TAG);

        RecyclerView productsList = (RecyclerView) findViewById(R.id.products_list);
        productsList.setLayoutManager(new GridLayoutManager(this, 2));
        ProductListAdapter productListAdapter = new ProductListAdapter();
        productsList.setAdapter(productListAdapter);

        new FetchProductsTask(productListAdapter).execute(productCategory.id);
    }
}
