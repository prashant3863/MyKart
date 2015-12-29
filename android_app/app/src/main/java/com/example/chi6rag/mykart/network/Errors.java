package com.example.chi6rag.mykart.network;

import com.google.gson.annotations.SerializedName;

public class Errors {
    @SerializedName("base")
    public String[] errorsCollection;

    public boolean containsErrorLike(String errorSubstring) {
        for (String errorDefinition : errorsCollection) {
            if (errorDefinition.contains(errorSubstring)) return true;
        }
        return false;
    }
}
