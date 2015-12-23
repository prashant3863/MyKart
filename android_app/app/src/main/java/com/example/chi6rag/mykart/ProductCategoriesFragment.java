package com.example.chi6rag.mykart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.chi6rag.mykart.adapters.ProductCategoriesListAdapter;
import com.example.chi6rag.mykart.async_tasks.FetchCategoriesBasedOnGenderTask;
import com.example.chi6rag.mykart.models.CategoryResource;
import com.example.chi6rag.mykart.models.ProductCategory;

public class ProductCategoriesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_categories_fragment, container, false);

        final ListView productCategoriesList = (ListView) view.findViewById(R.id.product_categories_list);
        final ProductCategoriesListAdapter productCategoriesListAdapter = new ProductCategoriesListAdapter(getActivity());
        RelativeLayout productCategoriesProgressContainer = (RelativeLayout) view
                .findViewById(R.id.product_categories_progress_container);
        productCategoriesList.setAdapter(productCategoriesListAdapter);
        Bundle bundle = getArguments();

        new FetchCategoriesBasedOnGenderTask(getContext(),
                selectedGender(bundle),
                productCategoriesListAdapter,
                productCategoriesProgressContainer,
                productCategoriesList)
                .execute();

        productCategoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductCategory productCategory = productCategoriesListAdapter.getItem(position);

                Bundle bundle = new Bundle();
                bundle.putParcelable(ProductCategory.TAG, productCategory);

                ProductsFragment productsFragment = new ProductsFragment();
                productsFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_layout, productsFragment)
                        .commit();
            }
        });

        return view;
    }

    private String selectedGender(Bundle bundle) {
        return bundle.getString(CategoryResource.TAG);
    }
}
