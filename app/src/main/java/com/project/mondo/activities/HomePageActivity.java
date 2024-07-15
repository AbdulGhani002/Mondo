package com.project.mondo.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.mondo.R;
import com.project.mondo.models.TopStoriesResponse;
import com.project.mondo.network.NYTimesApi;
import com.project.mondo.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.project.mondo.BuildConfig;


public class HomePageActivity extends AppCompatActivity {

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

        fetchTopStories("home");
    }

    private void fetchTopStories(String section) {
        NYTimesApi apiService = RetrofitClient.getClient().create(NYTimesApi.class);
        String API_KEY = BuildConfig.API_KEY;
        Call<TopStoriesResponse> call = apiService.getTopStories(section, API_KEY);

        call.enqueue(new Callback<TopStoriesResponse>() {
            @Override
            public void onResponse(Call<TopStoriesResponse> call, Response<TopStoriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TopStoriesResponse topStories = response.body();
                    handleTopStories(topStories);
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

    private void handleTopStories(TopStoriesResponse topStories) {
        if (!topStories.getStories().isEmpty()) {
            System.out.println(topStories.getStories().get(0).getTitle());
            Log.d("HomePageActivity", topStories.getStories().get(0).getTitle());
        }
    }
}
