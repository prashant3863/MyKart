package com.example.chi6rag.mykart;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chi6rag.mykart.models.Product;
import com.example.chi6rag.mykart.view_models.ProductViewModel;
import com.squareup.picasso.Picasso;

public class ProductDetailFragment extends Fragment {
    private View fragmentView;
    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private TextView productDescription;
    private Button addToCartButton;
    private String host;
    private String port;
    private OnInteractionListener addToCartButtonListener;

    public interface OnInteractionListener {
        public void onAddToCartButtonClick(Product product);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.addToCartButtonListener = ((OnInteractionListener) activity);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(R.layout.product_detail_fragment, container, false);
        Bundle arguments = getArguments();
        this.host = this.getString(R.string.host);
        this.port = this.getString(R.string.port);
        this.productImage = (ImageView) fragmentView.findViewById(R.id.product_image);
        this.productName = (TextView) fragmentView.findViewById(R.id.product_name);
        this.productPrice = (TextView) fragmentView.findViewById(R.id.product_price);
        this.productDescription = (TextView) fragmentView.findViewById(R.id.product_description);
        this.addToCartButton = (Button) fragmentView.findViewById(R.id.add_to_cart_button);

        Product product = null;
        if (arguments != null) {
            product = arguments.getParcelable(Product.TAG);
        }
        if (product != null) {
            populate(product);
        } else {
            Log.d("chi6rag", "No Product Selected");
        }

        final Product productToAddToCart = product;
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCartButtonListener.onAddToCartButtonClick(productToAddToCart);
            }
        });

        return fragmentView;
    }

    public void populate(Product product) {
        ProductViewModel productViewModel = new ProductViewModel(product, getResources());

        this.productName.setText(product.name);
        productPrice.setText(productViewModel.formattedPrice());

        Picasso.with(getContext())
                .load(host + port + product.firstImageResource().largeUrl)
                .placeholder(R.drawable.m_placeholder_2)
                .into(productImage);

        productDescription.setText(product.description);
    }
}
