package com.project.mondo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mondo.activities.ArticleActivity;
import com.project.mondo.R;
import com.project.mondo.models.Article;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<Article> articleList;
    private Context context;

    public NewsAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articleList = articles;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_article, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.titleTextView.setText(article.getTitle());

        holder.sectionTextView.setText(article.getSectionName() != null ? article.getSectionName() : "No section info");
        holder.publishedDateTextView.setText(article.getPublicationDate() != null ? article.getPublicationDate() : "No date available");

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ArticleActivity.class);
            intent.putExtra("article", article);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void updateData(List<Article> newArticleList) {
        this.articleList = newArticleList;
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        final TextView titleTextView;
        final TextView sectionTextView;
        final TextView publishedDateTextView;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textTitle);
            sectionTextView = itemView.findViewById(R.id.textSection);
            publishedDateTextView = itemView.findViewById(R.id.textPublishedDate);
        }
    }
}
