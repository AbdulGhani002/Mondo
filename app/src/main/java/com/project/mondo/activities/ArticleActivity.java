package com.project.mondo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.mondo.R;
import com.project.mondo.models.Article;
import com.project.mondo.models.TranslationResponse;
import com.project.mondo.network.TranslationService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.project.mondo.data.Nouns;


public class ArticleActivity extends AppCompatActivity {
    private static final String TAG = "ArticleActivity";
    private TextView titleTextView;
    private TextView bodyTextView;
    private static final int DELAY_MS = 4000;
    private static final Set<String> NOUNS = Nouns.NOUNS;

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
                        bodyTextView.setText(Html.fromHtml(body, Html.FROM_HTML_MODE_COMPACT));
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

    private void translateAndHighlightText(String originalHtml) {
        Document document = Jsoup.parse(originalHtml);
        List<Element> elements = document.body().getAllElements();
        Handler handler = new Handler(Looper.getMainLooper());

        for (Element element : elements) {
            String elementText = element.ownText();
            if (!elementText.trim().isEmpty()) {
                String[] words = elementText.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    final String word = words[i];
                    final int index = i;
                    handler.postDelayed(() -> {
                        if (isNoun(word)) {
                            translateText(word, translatedText -> {
                                runOnUiThread(() -> {
                                    if (translatedText.equals("Translation Failed") || translatedText.equals("Translation Failed: No translations found") || translatedText.equals("Translation Failed: No noun translations found")) {
                                        Log.e(TAG, "Translation Failed for word: " + word);
                                        return;
                                    }
                                    String highlightedText = highlightText(translatedText);
                                    element.html(element.html().replaceFirst(word, highlightedText));
                                    bodyTextView.setText(Html.fromHtml(document.html(), Html.FROM_HTML_MODE_COMPACT));
                                    Log.d(TAG, "Updated HTML: " + document.html());
                                });
                            });
                        }
                    }, index * DELAY_MS);
                }
            }
        }
    }

    private boolean isNoun(String word) {
        return NOUNS.contains(word.toLowerCase());
    }

    private String highlightText(String text) {
        return "<span style=\"color: red; background-color: yellow; font-weight: bold;\">" +
                escapeHtml(text) + "</span>";
    }

    private String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private void translateText(String text, TranslationCallback callback) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.18.68:8999/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();

        TranslationService service = retrofit.create(TranslationService.class);

        Call<TranslationResponse> call = service.translate(text, "en", "it");
        makeCallWithRetry(call, 3, 4000, callback);
    }

    private void makeCallWithRetry(Call<TranslationResponse> call, int retries, long delay, TranslationCallback callback) {
        call.enqueue(new Callback<TranslationResponse>() {
            @Override
            public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TranslationResponse.Translations translations = response.body().getTranslations();
                    if (translations != null) {
                        List<TranslationResponse.TranslationItem> nounTranslations = translations.getNoun();
                        if (nounTranslations != null && !nounTranslations.isEmpty()) {
                            String translatedText = nounTranslations.get(0).getTranslation();
                            callback.onTranslationComplete(translatedText);
                        } else {
                            callback.onTranslationComplete("Translation Failed: No noun translations found");
                        }
                    } else {
                        callback.onTranslationComplete("Translation Failed: No translations found");
                    }
                } else {
                    Log.e(TAG, "API Error: " + response.message());
                    Log.d(TAG, "Response Body: " + response.errorBody());
                    handleFailure(call, retries, delay, callback);
                }
            }

            @Override
            public void onFailure(Call<TranslationResponse> call, Throwable t) {
                Log.e(TAG, "Request Failure: ", t);
                handleFailure(call, retries, delay, callback);
            }
        });
    }

    private void handleFailure(Call<TranslationResponse> call, int retries, long delay, TranslationCallback callback) {
        if (retries > 0) {
            Log.e(TAG, "Retrying request. Retries left: " + retries);
            new Handler(Looper.getMainLooper()).postDelayed(() -> makeCallWithRetry(call.clone(), retries - 1, delay, callback), delay);
        } else {
            callback.onTranslationComplete("Translation Failed");
        }
    }

    private interface TranslationCallback {
        void onTranslationComplete(String translatedText);
    }
}
