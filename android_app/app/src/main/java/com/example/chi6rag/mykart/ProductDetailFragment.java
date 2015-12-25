package com.example.chi6rag.mykart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    ImageView productImage;
    TextView productName;
    TextView productPrice;
    TextView productDescription;
    Button addToCartButton;
    String host;
    String port;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_detail_fragment, container, false);
        host = this.getString(R.string.host);
        port = this.getString(R.string.port);

        productImage = (ImageView) view.findViewById(R.id.product_image);
        productName = (TextView) view.findViewById(R.id.product_name);
        productPrice = (TextView) view.findViewById(R.id.product_price);
        productDescription = (TextView) view.findViewById(R.id.product_description);
        addToCartButton = (Button) view.findViewById(R.id.add_to_cart_button);

//        final Product product = getArguments().getParcelable(Product.TAG);
//
////        addToCartButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String orderNumber = fetchCurrentOrderNumber();
////                if (orderNumber == null) {
////                    new CreateOrderTask(getApplicationContext()).execute();
////                }
////            }
////        });
        return view;
    }

    public void populate(Product product) {
        ProductViewModel productViewModel = new ProductViewModel(product, getResources());

        productName.setText(product.name);
        productPrice.setText(productViewModel.formattedPrice());

        Picasso.with(getContext())
                .load(host + port + product.firstImageResource().largeUrl)
                .placeholder(R.drawable.m_placeholder_2)
                .into(productImage);

        productDescription.setText(product.description);
    }
}
