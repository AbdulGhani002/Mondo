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
import com.project.mondo.models.ArticleSearchResponse;
import com.project.mondo.network.GuardianAPIService;
import com.project.mondo.network.RetrofitClient;

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

        setupInsets();
        setupRecyclerView();
        fetchGuardianArticles("latest");
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

    private void fetchGuardianArticles(String query) {
        GuardianAPIService apiService = RetrofitClient.getClient().create(GuardianAPIService.class);
        String apiKey = "40a29f60-207c-4e81-9fda-addb4f7bc4f0";
        Call<ArticleSearchResponse> call = apiService.searchArticles(apiKey, query);

        call.enqueue(new Callback<ArticleSearchResponse>() {
            @Override
            public void onResponse(Call<ArticleSearchResponse> call, Response<ArticleSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArticleSearchResponse articleResponse = response.body();
                    List<ArticleSearchResponse.Response.Article> articles = articleResponse.getResponse().getResults();
                    if (articles != null && !articles.isEmpty()) {
                        newsAdapter.updateData(articles);
                    } else {
                        Toast.makeText(HomePageActivity.this, "No articles found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    handleApiError(response);
                }
            }

            @Override
            public void onFailure(Call<ArticleSearchResponse> call, Throwable t) {
                handleNetworkError(t);
            }
        });
    }

    private void handleApiError(Response<ArticleSearchResponse> response) {
        Toast.makeText(HomePageActivity.this, "Failed to fetch articles: " + response.message(), Toast.LENGTH_SHORT).show();
        Log.e("HomePageActivity", "Failed to fetch articles: " + response.message());
    }

    private void handleNetworkError(Throwable t) {
        Toast.makeText(HomePageActivity.this, "Network error occurred", Toast.LENGTH_SHORT).show();
        Log.e("HomePageActivity", "Error fetching articles", t);
    }
}
