package com.project.mondo.network;

import com.project.mondo.models.ArticleSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GuardianAPIService {
    @GET("search")
    Call<ArticleSearchResponse> searchArticles(
            @Query("api-key") String apiKey,
            @Query("q") String query
    );
}
