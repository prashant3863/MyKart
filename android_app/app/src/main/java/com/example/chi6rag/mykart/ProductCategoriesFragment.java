package com.example.chi6rag.mykart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chi6rag.mykart.adapters.ProductCategoriesListAdapter;
import com.example.chi6rag.mykart.models.CategoryResource;
import com.example.chi6rag.mykart.models.ProductCategory;

import java.util.List;

public class ProductCategoriesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CategoryResource categoryResource = getArguments().getParcelable(CategoryResource.TAG);

        View view = inflater.inflate(R.layout.product_categories_fragment, container, false);
        final ListView productCategoriesList = (ListView) view.findViewById(R.id.product_categories_list);
        final List<ProductCategory> productCategories = categoryResource.productCategories();

        productCategoriesList.setAdapter(new ProductCategoriesListAdapter(getActivity(), productCategories));

        productCategoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductCategory productCategory = productCategories.get(position);

                Intent productsActivityIntent = new Intent(getContext(), ProductsActivity.class);
                productsActivityIntent.putExtra(ProductCategory.TAG, productCategory);
                startActivity(productsActivityIntent);
            }
        });
        return view;
    }
}
