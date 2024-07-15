package com.project.mondo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mondo.R;
import com.project.mondo.models.TopStoriesResponse;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<TopStoriesResponse.Story> articles;

    public NewsAdapter(List<TopStoriesResponse.Story> articles) {
        this.articles = articles;
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
        TopStoriesResponse.Story article = articles.get(position);
        holder.bind(article);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void updateData(List<TopStoriesResponse.Story> newData) {
        articles.clear();
        articles.addAll(newData);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView abstractTextView;
        private TextView bylineTextView;
        private TextView publishedDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textTitle);
            abstractTextView = itemView.findViewById(R.id.textAbstract);
            bylineTextView = itemView.findViewById(R.id.textByline);
            publishedDateTextView = itemView.findViewById(R.id.textPublishedDate);
        }

        public void bind(TopStoriesResponse.Story article) {
            titleTextView.setText(article.getTitle());
            abstractTextView.setText(article.getStoryAbstract());
            bylineTextView.setText(article.getByline());
            publishedDateTextView.setText(article.getPublishedDate());
        }
    }
}
