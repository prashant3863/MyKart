package com.example.chi6rag.mykart.async_tasks;

public interface Callback<T> {
    void onSuccess(T t);

    void onFailure();
}
