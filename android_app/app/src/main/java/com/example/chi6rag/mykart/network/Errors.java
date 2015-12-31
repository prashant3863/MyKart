package com.example.chi6rag.mykart.network;

import com.google.gson.annotations.SerializedName;

public class Errors {
    @SerializedName("base")
    public String[] errorsCollection;

    public Errors(String... errorsCollection) {
        this.errorsCollection = errorsCollection;
    }

    public boolean containsErrorLike(String errorSubstring) {
        for (String errorDefinition : errorsCollection) {
            if (errorDefinition.contains(errorSubstring)) return true;
        }
        return false;
    }

    public boolean isNotEmpty() {
        return (isErrorsCollectionNull() && isErrorsCollectionLengthGreaterThanOrEqualToOne());
    }

    private boolean isErrorsCollectionLengthGreaterThanOrEqualToOne() {
        return this.errorsCollection.length >= 1;
    }

    private boolean isErrorsCollectionNull() {
        return this.errorsCollection != null;
    }

    public int count() {
        return errorsCollection.length;
    }

    public String get(int errorPosition) {
        return this.errorsCollection[errorPosition];
    }
}
