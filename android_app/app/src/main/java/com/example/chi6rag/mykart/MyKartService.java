package com.example.chi6rag.mykart;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MyKartService {
    @GET("api/taxonomies/{taxonomy_id}")
    Call<Category> getCategory(@Path("taxonomy_id") int taxonomyId);
}
