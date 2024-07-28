package com.project.mondo.network;

import com.project.mondo.models.TranslationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TranslationService {
    @GET("api")
    Call<TranslationResponse> translate(
            @Query("text") String text,
            @Query("from") String from,
            @Query("to") String to
    );

    @GET("languages")
    Call<String> getLanguages();
}
