package com.project.mondo.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mondo.R;
import com.project.mondo.models.Article;
import com.project.mondo.network.GuardianAPIService;
import com.project.mondo.network.RetrofitClient;
import com.project.mondo.models.ArticleResponse;
import com.project.mondo.adapters.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

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

        initializeUI();
        fetchGuardianArticles("latest");
    }

    private void initializeUI() {
        setupInsets();
        setupRecyclerView();
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
        newsAdapter = new NewsAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(newsAdapter);
    }

    private void fetchGuardianArticles(@NonNull String query) {
        GuardianAPIService apiService = RetrofitClient.getClient().create(GuardianAPIService.class);
        String apiKey = "40a29f60-207c-4e81-9fda-addb4f7bc4f0";

        Call<ArticleResponse> call = apiService.searchArticles(
                apiKey,
                "2024-01-01",
                "2024-12-31",
                "newest",
                "headline,body",
                20,
                query
        );

        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(@NonNull Call<ArticleResponse> call, @NonNull Response<ArticleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleSuccessfulResponse(response.body());
                } else {
                    handleApiError(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArticleResponse> call, @NonNull Throwable t) {
                handleNetworkError(t);
            }
        });
    }

    private void handleSuccessfulResponse(@NonNull ArticleResponse articleResponse) {
        List<Article> articles = articleResponse.getResponse().getResults();
        if (articles != null && !articles.isEmpty()) {
            newsAdapter.updateData(articles);
        } else {
            showToast("No articles found");
        }
    }

    private void handleApiError(@NonNull Response<ArticleResponse> response) {
        showToast("Failed to fetch articles: " + response.message());
        Log.e("HomePageActivity", "API error: " + response.message());
    }

    private void handleNetworkError(@NonNull Throwable t) {
        showToast("Network error occurred");
        Log.e("HomePageActivity", "Network error", t);
    }

    private void showToast(@NonNull String message) {
        Toast.makeText(HomePageActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
