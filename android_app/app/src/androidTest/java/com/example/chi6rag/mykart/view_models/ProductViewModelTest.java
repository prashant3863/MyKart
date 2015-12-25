package com.example.chi6rag.mykart.view_models;

import com.example.chi6rag.mykart.models.Product;

import org.junit.Test;

public class ProductViewModelTest {
    @Test
    public void testBla() {
        Product product = new Product("example_product", 1000);
        ProductViewModel productViewModel = new ProductViewModel(product);
        assertEquals("Rs. 1000", productViewModel.formattedPrice());
    }
}