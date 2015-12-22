package com.example.chi6rag.mykart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.chi6rag.mykart.models.CategoryResource;

public class LandingFragment extends Fragment {
    private static final String LOG_TAG = "chi6rag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.landing_fragment, container, false);

        RelativeLayout menSelection = (RelativeLayout) view.findViewById(R.id.men_section);
        RelativeLayout womenSelection = (RelativeLayout) view.findViewById(R.id.women_section);

        menSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "queried products for men");
                Bundle fragmentArguments = new Bundle();
                fragmentArguments.putString(CategoryResource.TAG, CategoryResource.MEN);

                ProductCategoriesFragment productCategoriesFragment = new ProductCategoriesFragment();
                productCategoriesFragment.setArguments(fragmentArguments);

                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(LandingFragment.class.getName())
                        .replace(R.id.activity_main_layout, productCategoriesFragment)
                        .commit();
            }
        });

        womenSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "queried products for women");
            }
        });
        return view;
    }
}
