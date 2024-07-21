package com.project.mondo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.mondo.R;
import com.project.mondo.models.Article;
import com.project.mondo.models.TranslationResponse;
import com.project.mondo.network.TranslationService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleActivity extends AppCompatActivity {
    private static final String TAG = "ArticleActivity";
    private TextView titleTextView;
    private TextView bodyTextView;
    private static final int CHUNK_SIZE = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity);

        titleTextView = findViewById(R.id.textTitleDetail);
        bodyTextView = findViewById(R.id.bodyTextView);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("article")) {
            Article article = (Article) intent.getSerializableExtra("article");

            if (article != null) {
                titleTextView.setText(article.getTitle() != null ? article.getTitle() : "No Title");
                if (article.getFields() != null) {
                    String body = article.getFields().getBody();
                    if (body != null) {
                        translateAndHighlightText(body);
                    } else {
                        bodyTextView.setText("No Content");
                    }
                } else {
                    bodyTextView.setText("No Content");
                }
            } else {
                titleTextView.setText("No Article Data");
                bodyTextView.setText("");
            }
        }
    }

    private void translateAndHighlightText(String body) {
        List<String> chunks = splitTextIntoChunks(body, CHUNK_SIZE);
        for (String chunk : chunks) {
            translateText(chunk, translatedText -> {
                runOnUiThread(() -> {
                    bodyTextView.append(Html.fromHtml(translatedText, Html.FROM_HTML_MODE_COMPACT) + " ");
                });
            });
        }
    }

    private List<String> splitTextIntoChunks(String text, int chunkSize) {
        List<String> chunks = new ArrayList<>();
        String[] words = text.split("\\s+");
        StringBuilder chunk = new StringBuilder();
        for (String word : words) {
            if (chunk.length() + word.length() > chunkSize) {
                chunks.add(chunk.toString().trim());
                chunk.setLength(0);
            }
            chunk.append(word).append(" ");
        }
        if (chunk.length() > 0) {
            chunks.add(chunk.toString().trim());
        }
        return chunks;
    }

    private void translateText(String text, TranslationCallback callback) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8999/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();

        TranslationService service = retrofit.create(TranslationService.class);


        Call<TranslationResponse> call = service.translate(text, "en", "it");

        call.enqueue(new Callback<TranslationResponse>() {
            @Override
            public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    String translatedText = response.body().getResult();
                    callback.onTranslationComplete(translatedText);
                } else {
                    Log.e(TAG, "Error: " + response.message());
                    try {
                        Log.e(TAG, "Error body: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    callback.onTranslationComplete("Translation Failed");
                }
            }

            @Override
            public void onFailure(Call<TranslationResponse> call, Throwable t) {
                Log.e(TAG, "Error: ", t);
                callback.onTranslationComplete("Translation Error: " + t.getMessage());
            }
        });
    }

    private interface TranslationCallback {
        void onTranslationComplete(String translatedText);
    }
}
