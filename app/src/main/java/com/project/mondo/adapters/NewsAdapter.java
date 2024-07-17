package com.project.mondo.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mondo.R;
import com.project.mondo.activities.ArticleActivity;
import com.project.mondo.models.NewsDiffCallback;
import com.project.mondo.models.TopStoriesResponse;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<TopStoriesResponse.Story> stories;
    private Context context;

    public NewsAdapter(List<TopStoriesResponse.Story> stories, Context context) {
        this.stories = stories;
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
        TopStoriesResponse.Story story = stories.get(position);
        holder.bind(story);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ArticleActivity.class);
            intent.putExtra("story", story);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void updateData(List<TopStoriesResponse.Story> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new NewsDiffCallback(this.stories, newData));
        stories.clear();
        stories.addAll(newData);
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

        public void bind(TopStoriesResponse.Story story) {
            titleTextView.setText(story.getTitle());
            abstractTextView.setText(story.getStoryAbstract());
            bylineTextView.setText(story.getByline());
            publishedDateTextView.setText(story.getPublishedDate());
        }
    }
}
