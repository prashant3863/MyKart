package com.example.chi6rag.mykart.async_tasks;

public interface StatusCallback<T> {
    void onSuccess(T t);

    void onFailure();
}
