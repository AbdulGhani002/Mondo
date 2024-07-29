package com.project.mondo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.mondo.R;
import com.project.mondo.adapters.WordInfo;
import com.project.mondo.models.Article;
import com.project.mondo.models.TranslationResponse;
import com.project.mondo.network.TranslationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import android.text.method.LinkMovementMethod;

public class ArticleActivity extends AppCompatActivity {
    private static final String TAG = "ArticleActivity";
    private TextView titleTextView;
    private TextView bodyTextView;
    private static final Set<String> NOUNS = Nouns.NOUNS;
    private List<WordInfo> translatedWords = new ArrayList<>();

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

        for (Element element : elements) {
            String elementText = element.ownText();
            if (!elementText.trim().isEmpty()) {
                String[] words = elementText.split("\\s+");
                for (String word : words) {
                    if (isNoun(word)) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(() -> {
                            translateText(word, translatedText -> {
                                runOnUiThread(() -> {
                                    if (translatedText.equals("Translation Failed") || translatedText.equals("Translation Failed: No translations found") || translatedText.equals("Translation Failed: No noun translations found")) {
                                        Log.e(TAG, "Translation Failed for word: " + word);
                                        return;
                                    }
                                    WordInfo wordInfo = new WordInfo(word, translatedText, null, null, null);
                                    translatedWords.add(wordInfo);
                                    applyTranslationForWord(wordInfo);
                                });
                            });
                        }, 500);
                    }
                }
            }
        }

        Spanned spannedText = Html.fromHtml(originalHtml, Html.FROM_HTML_MODE_COMPACT);
        String text = spannedText.toString();

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        HashMap<String, Integer> nounIndexMap = new HashMap<>();
        int index = 0;
        for (Element element : elements) {
            String elementText = element.ownText();
            if (!elementText.trim().isEmpty()) {
                String[] words = elementText.split("\\s+");
                for (String w : words) {
                    if (isNoun(w)) {
                        nounIndexMap.put(w, index);
                    }
                    spannableStringBuilder.append(w).append(" ");
                    index += w.length() + 1;
                }
            }
        }


        bodyTextView.setText(spannableStringBuilder);
    }

    private void applyTranslationForWord(WordInfo wordInfo) {
        String word = wordInfo.getWord();
        String translatedText = wordInfo.getTranslation();
        if (translatedText == null) {
            return;
        }

        Document document = Jsoup.parse(bodyTextView.getText().toString());
        List<Element> elements = document.body().getAllElements();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        HashMap<String, Integer> nounIndexMap = new HashMap<>();
        int index = 0;
        for (Element element : elements) {
            String elementText = element.ownText();
            if (!elementText.trim().isEmpty()) {
                String[] words = elementText.split("\\s+");
                for (String w : words) {
                    if (isNoun(w)) {
                        nounIndexMap.put(w, index);
                    }
                    spannableStringBuilder.append(w).append(" ");
                    index += w.length() + 1;
                }
            }
        }

        Integer indexPosition = nounIndexMap.get(word);
        String highlightedText = "";
        if (indexPosition!= null) {
            highlightedText = highlightText(translatedText);
            Spanned spannedText = (Spanned) bodyTextView.getText();
            SpannableStringBuilder modifiedSpannableStringBuilder = new SpannableStringBuilder(spannedText);
            modifiedSpannableStringBuilder.replace(indexPosition, indexPosition + word.length(), highlightedText);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    showWordDetailsPopup(wordInfo);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setTextSize(24);
                }
            };
            modifiedSpannableStringBuilder.setSpan(clickableSpan, indexPosition, indexPosition + highlightedText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            bodyTextView.setText(modifiedSpannableStringBuilder);
        }

        bodyTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private int getNounCount(String originalHtml) {
        Document document = Jsoup.parse(originalHtml);
        List<Element> elements = document.body().getAllElements();
        int nounCount = 0;

        for (Element element : elements) {
            String elementText = element.ownText();
            if (!elementText.trim().isEmpty()) {
                String[] words = elementText.split("\\s+");
                for (String word : words) {
                    if (isNoun(word)) {
                        nounCount++;
                    }
                }
            }
        }

        return nounCount;
    }

    private void showWordDetailsPopup(WordInfo wordInfo) {
        PopupWindow popupWindow = new PopupWindow(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.word_details_popup, null);
        popupWindow.setContentView(popupView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);

        TextView wordTextView = popupWindow.getContentView().findViewById(R.id.word_text_view);
        wordTextView.setText(wordInfo.getWord());
        TextView translationTextView = popupWindow.getContentView().findViewById(R.id.translation_text_view);
        translationTextView.setText(wordInfo.getTranslation());

        TextView pronunciationTextView = popupWindow.getContentView().findViewById(R.id.pronunciation_text_view);
        pronunciationTextView.setText(wordInfo.getPronunciation());

        TextView definitionTextView = popupWindow.getContentView().findViewById(R.id.definition_text_view);
        StringBuilder definitionText = new StringBuilder();
        if (wordInfo.getDefinitions() != null) {
            for (WordInfo.Definition definition : wordInfo.getDefinitions()) {
                definitionText.append(definition.getDefinition()).append("\n");
            }
        }
        definitionTextView.setText(definitionText.toString());

        TextView exampleTextView = popupWindow.getContentView().findViewById(R.id.example_text_view);
        StringBuilder exampleText = new StringBuilder();
        if (wordInfo.getDefinitions() != null) {
            for (WordInfo.Definition definition : wordInfo.getDefinitions()) {
                exampleText.append(definition.getExample()).append("\n");
            }
        }
        exampleTextView.setText(exampleText.toString());

        TextView suggestionTextView = popupWindow.getContentView().findViewById(R.id.suggestion_text_view);
        StringBuilder suggestionText = new StringBuilder();
        if (wordInfo.getSuggestions() != null) {
            for (WordInfo.Suggestion suggestion : wordInfo.getSuggestions()) {
                suggestionText.append(suggestion.getText()).append("\n");
            }
        }
        suggestionTextView.setText(suggestionText.toString());

        popupWindow.showAtLocation(bodyTextView, Gravity.CENTER, 0, 0);
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
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    private interface TranslationCallback {
        void onTranslationComplete(String translatedText);
    }
}
