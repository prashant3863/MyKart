package com.example.chi6rag.mykart.view_models;

import com.example.chi6rag.mykart.network.Errors;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ErrorsViewModelTest {
    @Test
    public void formattedErrorsShouldBeEmptyIfNoErrorsArePresent() {
        Errors errors = new Errors(null);
        ErrorsViewModel errorsViewModel = new ErrorsViewModel(errors);
        assertEquals("", errorsViewModel.formattedErrors());
    }

    @Test
    public void shouldFormatFirstErrorIfOnlyOneErrorIsPresent() {
        Errors errors = new Errors("First Error");
        ErrorsViewModel errorsViewModel = new ErrorsViewModel(errors);
        assertEquals("- First Error", errorsViewModel.formattedErrors());
    }

    @Test
    public void shouldFormatErrorsOnDifferentLinesForMultipleErrors() {
        Errors errors = new Errors("First Error", "Second Error");
        ErrorsViewModel errorsViewModel = new ErrorsViewModel(errors);
        assertEquals("- First Error\n- Second Error", errorsViewModel.formattedErrors());
    }
}