package com.example.chi6rag.mykart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.chi6rag.mykart.adapters.ProductCategoriesListAdapter;
import com.example.chi6rag.mykart.models.Category;
import com.example.chi6rag.mykart.models.CategoryResource;

public class ProductCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_categories);
        Intent intent = getIntent();
        CategoryResource categoryResource = intent.getParcelableExtra(CategoryResource.TAG);

        ListView productCategoriesList = (ListView) findViewById(R.id.product_categories_list);
        productCategoriesList.setAdapter(new ProductCategoriesListAdapter(this, categoryResource.productCategories()));
    }
}
