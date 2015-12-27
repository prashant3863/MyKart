package com.example.chi6rag.mykart.view_models;

import android.content.res.Resources;

import com.example.chi6rag.mykart.models.Product;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class ProductViewModelTest {
    @Test
    public void testBla() {
        Product product = new Product("example_product", 1000);
        Resources resources = mock(Resources.class);
        ProductViewModel productViewModel = new ProductViewModel(product);
        assertEquals("Rs. 1000", productViewModel.formattedPrice());
    }
}