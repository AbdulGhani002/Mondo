package com.project.mondo.network;

import com.project.mondo.models.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GuardianAPIService {
    @GET("search")
    Call<ArticleResponse> searchArticles(
            @Query("api-key") String apiKey,
            @Query("from-date") String fromDate,
            @Query("to-date") String toDate,
            @Query("order-by") String orderBy,
            @Query("show-fields") String showFields,
            @Query("page-size") int pageSize,
            @Query("q") String query
    );
}
