package com.example.chi6rag.mykart.view_models;

import android.content.res.Resources;

import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.models.Product;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductViewModelTest {
    @Test
    public void testBla() {
        Product product = new Product("example_product", 1000);
        Resources resources = mock(Resources.class);
        when(resources.getString(R.string.indian_currency)).thenReturn("");
        ProductViewModel productViewModel = new ProductViewModel(product, resources);
        assertEquals("Rs. 1000", productViewModel.formattedPrice());
    }
}
