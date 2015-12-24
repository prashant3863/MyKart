package com.example.chi6rag.mykart;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.chi6rag.mykart.network.CategoryResource;

public class LandingFragment extends Fragment {
    private OnLandingScreenCategoryClickListener listener;

    public interface OnLandingScreenCategoryClickListener {
        public void onLandingScreenCategoryClick(String gender);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnLandingScreenCategoryClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnLandingScreenCategoryClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.landing_fragment, container, false);

        RelativeLayout menSelection = (RelativeLayout) view.findViewById(R.id.men_section);
        RelativeLayout womenSelection = (RelativeLayout) view.findViewById(R.id.women_section);

        menSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLandingScreenCategoryClick(CategoryResource.MEN);
            }
        });

        womenSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLandingScreenCategoryClick(CategoryResource.WOMEN);
            }
        });
        return view;
    }
}
