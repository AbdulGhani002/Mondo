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
import com.project.mondo.models.TopStoriesResponse;

import java.io.Serializable;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<TopStoriesResponse.Story> articles;
    private Context context;

    public NewsAdapter(List<TopStoriesResponse.Story> articles, Context context) {
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
        TopStoriesResponse.Story article = articles.get(position);
        holder.bind(article);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ArticleActivity.class);
            intent.putExtra("article", (Serializable) article);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void updateData(List<TopStoriesResponse.Story> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new NewsDiffCallback(this.articles, newData));
        articles.clear();
        articles.addAll(newData);
        diffResult.dispatchUpdatesTo(this);
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

    class NewsDiffCallback extends DiffUtil.Callback {

        private final List<TopStoriesResponse.Story> oldList;
        private final List<TopStoriesResponse.Story> newList;

        public NewsDiffCallback(List<TopStoriesResponse.Story> oldList, List<TopStoriesResponse.Story> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            TopStoriesResponse.Story oldItem = oldList.get(oldItemPosition);
            TopStoriesResponse.Story newItem = newList.get(newItemPosition);
            return oldItem.equals(newItem);
        }
    }
}
