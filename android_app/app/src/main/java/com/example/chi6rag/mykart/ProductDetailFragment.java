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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String host = this.getString(R.string.host);
        String port = this.getString(R.string.port);

        View view = inflater.inflate(R.layout.product_detail_fragment, container, false);

        ImageView productImage = (ImageView) view.findViewById(R.id.product_image);
        TextView productName = (TextView) view.findViewById(R.id.product_name);
        TextView productPrice = (TextView) view.findViewById(R.id.product_price);
        TextView productDescription = (TextView) view.findViewById(R.id.product_description);
        Button addToCartButton = (Button) view.findViewById(R.id.add_to_cart_button);

        final Product product = getArguments().getParcelable(Product.TAG);
        ProductViewModel productViewModel = new ProductViewModel(product, getResources());

        productName.setText(product.name);
        productPrice.setText(productViewModel.formattedPrice());

        Picasso.with(getContext())
                .load(host + port + product.firstImageResource().largeUrl)
                .placeholder(R.drawable.m_placeholder_2)
                .into(productImage);

        productDescription.setText(product.description);

//        addToCartButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String orderNumber = fetchCurrentOrderNumber();
//                if (orderNumber == null) {
//                    new CreateOrderTask(getApplicationContext()).execute();
//                }
//            }
//        });
        return view;
    }
}
