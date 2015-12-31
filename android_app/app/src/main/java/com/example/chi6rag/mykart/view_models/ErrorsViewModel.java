package com.example.chi6rag.mykart.view_models;

import com.example.chi6rag.mykart.network.Errors;

public class ErrorsViewModel {
    private static final String PREFIX = "- ";
    private final Errors errors;

    public ErrorsViewModel(Errors errors) {
        this.errors = errors;
    }

    public String formattedErrors() {
        String formattedErrors = "";
        if (this.errors.isNotEmpty()) {
            for (int errorPosition = 0; errorPosition < this.errors.count(); errorPosition++) {
                formattedErrors = formattedErrors.concat(PREFIX + this.errors.get(errorPosition));
                if (errorPosition + 1 < this.errors.count())
                    formattedErrors = formattedErrors.concat("\n");
            }
        }
        return formattedErrors;
    }
}
