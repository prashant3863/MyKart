package com.example.chi6rag.mykart;

import android.app.Activity;
import android.content.Context;
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
import com.example.chi6rag.mykart.network.CategoryResource;
import com.example.chi6rag.mykart.network.ProductCategory;

public class ProductCategoriesFragment extends Fragment {
    private OnProductCategoryClickListener listener;

    public interface OnProductCategoryClickListener {
        public void onProductCategoryClick(ProductCategory productCategory);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(getContext());
        Activity activity = getActivity();
        try {
            listener = (OnProductCategoryClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnProductCategoryClickListener");
        }
    }

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
                listener.onProductCategoryClick(productCategory);
            }
        });
        return view;
    }

    private String selectedGender(Bundle bundle) {
        return bundle.getString(CategoryResource.TAG);
    }
}
