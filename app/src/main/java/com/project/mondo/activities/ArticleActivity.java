package com.project.mondo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.mondo.R;
import com.project.mondo.models.TopStoriesResponse;

public class ArticleActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView bodyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity);

        titleTextView = findViewById(R.id.textView);
        bodyTextView = findViewById(R.id.textView2);

        Intent intent = getIntent();
        TopStoriesResponse.Story article = intent.getParcelableExtra("story");

        if (article != null) {
            titleTextView.setText(article.getTitle());
            Log.d("ArticleActivity", "Article received: " + article.getTitle());
            bodyTextView.setText(article.getStoryAbstract());
        } else {
            Log.e("ArticleActivity", "Article is null");
        }
    }
}
