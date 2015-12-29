package com.example.chi6rag.mykart.async_tasks;

public interface ExtensibleStatusCallback<T> {
    void onSuccess(T t);

    void onFailure(T t);
}
