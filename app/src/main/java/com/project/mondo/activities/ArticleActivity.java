package com.project.mondo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.mondo.R;
import com.project.mondo.models.ArticleSearchResponse;

public class ArticleActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView bodyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity);

        titleTextView = findViewById(R.id.textView);
        bodyTextView = findViewById(R.id.bodyTextView);

        Intent intent = getIntent();
        ArticleSearchResponse.Response.Article article = intent.getParcelableExtra("article");

        if (article != null) {
            titleTextView.setText(article.getWebTitle());

            String bodyText = article.getFields() != null ? article.getFields().getBodyText() : "No content available";
            bodyTextView.setText(bodyText);

            Log.d("ArticleActivity", "Article received: " + article.getWebTitle());
            Log.d("ArticleActivity", "Body content: " + bodyText);
        } else {
            Log.e("ArticleActivity", "Article is null");
            titleTextView.setText("No title available");
            bodyTextView.setText("No content available");
        }
    }
}
