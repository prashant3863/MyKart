package com.example.chi6rag.mykart.async_tasks;

public interface UIExecutor<T> {
    void onPreExecute();

    void onPostExecute(T t);
}
