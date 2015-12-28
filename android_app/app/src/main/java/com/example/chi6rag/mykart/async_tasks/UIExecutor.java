package com.example.chi6rag.mykart.async_tasks;

import android.view.View;

public interface UIExecutor<T> {
    void onPreExecute();
    void onPostExecute(T t);
}
