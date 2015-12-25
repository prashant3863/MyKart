package com.example.chi6rag.mykart.view_models;

import android.content.res.Resources;

import com.example.chi6rag.mykart.R;
import com.example.chi6rag.mykart.models.Product;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductViewModelTest {

    @Test
    public void testFormattedPrice() throws Exception {
        Resources resources = mock(Resources.class);
        when(resources.getString(R.string.indian_currency)).thenReturn("Rs. ");

        Product product = new Product("Test Product", 1000.0);
        ProductViewModel productViewModel = new ProductViewModel(product, resources);

        assertEquals("Rs. 1000.0", productViewModel.formattedPrice());
    }
}