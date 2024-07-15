package com.project.mondo.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mondo.BuildConfig;
import com.project.mondo.R;
import com.project.mondo.adapters.NewsAdapter;
import com.project.mondo.models.TopStoriesResponse;
import com.project.mondo.network.NYTimesApi;
import com.project.mondo.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        setupInsets();
        setupRecyclerView();
        fetchTopStories("home");
    }

    private void setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homePage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(newsAdapter);
    }

    private void fetchTopStories(String section) {
        NYTimesApi apiService = RetrofitClient.getClient().create(NYTimesApi.class);
        String apiKey = BuildConfig.API_KEY;
        Call<TopStoriesResponse> call = apiService.getTopStories(section, apiKey);

        call.enqueue(new Callback<TopStoriesResponse>() {
            @Override
            public void onResponse(Call<TopStoriesResponse> call, Response<TopStoriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TopStoriesResponse topStories = response.body();
                    newsAdapter.updateData(topStories.getStories());
                } else {
                    handleApiError(response);
                }
            }

            @Override
            public void onFailure(Call<TopStoriesResponse> call, Throwable t) {
                handleNetworkError(t);
            }
        });
    }

    private void handleApiError(Response<TopStoriesResponse> response) {
        Toast.makeText(HomePageActivity.this, "Failed to fetch stories: " + response.message(), Toast.LENGTH_SHORT).show();
        Log.e("HomePageActivity", "Failed to fetch stories: " + response.message());
    }

    private void handleNetworkError(Throwable t) {
        Toast.makeText(HomePageActivity.this, "Network error occurred", Toast.LENGTH_SHORT).show();
        Log.e("HomePageActivity", "Error fetching stories", t);
    }
}
