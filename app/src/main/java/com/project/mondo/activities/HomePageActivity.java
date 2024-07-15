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

        // Adjust padding for edge-to-edge experience
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homePage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(new ArrayList<>()); // Initialize adapter with empty list
        recyclerView.setAdapter(newsAdapter);

        // Fetch and display top stories
        fetchTopStories("home"); // Example section
    }

    private void fetchTopStories(String section) {
        NYTimesApi apiService = RetrofitClient.getClient().create(NYTimesApi.class);
        String apiKey = BuildConfig.API_KEY; // Access the API key from BuildConfig
        Call<TopStoriesResponse> call = apiService.getTopStories(section, apiKey);

        call.enqueue(new Callback<TopStoriesResponse>() {
            @Override
            public void onResponse(Call<TopStoriesResponse> call, Response<TopStoriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TopStoriesResponse topStories = response.body();
                    assert topStories != null;
                    newsAdapter.updateData(topStories.getStories());
                } else {
                    Toast.makeText(HomePageActivity.this, "Failed to fetch stories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopStoriesResponse> call, Throwable t) {
                Toast.makeText(HomePageActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                Log.e("HomePageActivity", "Error fetching stories: " + t.getMessage(), t);
            }
        });
    }
}
