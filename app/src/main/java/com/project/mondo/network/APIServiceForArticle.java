package com.project.mondo.network;

import com.project.mondo.models.ArticleSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceForArticle {
    @GET("svc/search/v2/articlesearch.json")
    Call<ArticleSearchResponse> searchArticles(
            @Query("api-key") String apiKey,
            @Query("fq") String filterQuery
    );
}

