package com.example.chi6rag.mykart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chi6rag.mykart.adapters.ProductCategoriesListAdapter;
import com.example.chi6rag.mykart.models.CategoryResource;
import com.example.chi6rag.mykart.models.ProductCategory;

import java.util.List;

public class ProductCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_categories);
        final Intent intent = getIntent();
        final CategoryResource categoryResource = intent.getParcelableExtra(CategoryResource.TAG);

        final ListView productCategoriesList = (ListView) findViewById(R.id.product_categories_list);
        final List<ProductCategory> productCategories = categoryResource.productCategories();

        productCategoriesList.setAdapter(new ProductCategoriesListAdapter(this, productCategories));

        productCategoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductCategory productCategory = productCategories.get(position);

                Intent productsActivityIntent = new Intent(getApplicationContext(), ProductsActivity.class);
                productsActivityIntent.putExtra(ProductCategory.TAG, productCategory);
                startActivity(productsActivityIntent);
            }
        });
    }
}
