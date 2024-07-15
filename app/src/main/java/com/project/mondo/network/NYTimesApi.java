package com.project.mondo.network;

import com.project.mondo.models.TopStoriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NYTimesApi {
    @GET("{section}.json")
    Call<TopStoriesResponse> getTopStories(@Path("section") String section, @Query("api-key") String apiKey);
}

