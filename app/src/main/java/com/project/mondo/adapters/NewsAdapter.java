package com.project.mondo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mondo.R;
import com.project.mondo.activities.ArticleActivity;
import com.project.mondo.models.ArticleSearchResponse;
import com.project.mondo.models.NewsDiffCallback;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<ArticleSearchResponse.Response.Article> articles;
    private Context context;

    public NewsAdapter(List<ArticleSearchResponse.Response.Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticleSearchResponse.Response.Article article = articles.get(position);
        holder.bind(article);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ArticleActivity.class);
            intent.putExtra("articleUrl", article.getWebUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void updateData(List<ArticleSearchResponse.Response.Article> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new NewsDiffCallback(this.articles, newData));
        articles.clear();
        articles.addAll(newData);
        diffResult.dispatchUpdatesTo(this);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView abstractTextView;
        private TextView sectionTextView;
        private TextView publishedDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textTitle);
            abstractTextView = itemView.findViewById(R.id.textAbstract);
            sectionTextView = itemView.findViewById(R.id.textSection);
            publishedDateTextView = itemView.findViewById(R.id.textPublishedDate);
        }

        public void bind(ArticleSearchResponse.Response.Article article) {
            titleTextView.setText(article.getWebTitle());
            abstractTextView.setText(article.getFields() != null ? article.getFields().getTrailText() : "");
            sectionTextView.setText(article.getSectionName());
            // publishedDateTextView.setText(article.getPublishedDate()); // Modify as needed
        }
    }
}
