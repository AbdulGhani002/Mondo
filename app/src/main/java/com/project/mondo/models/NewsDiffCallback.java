package com.project.mondo.models;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class NewsDiffCallback extends DiffUtil.Callback {

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
        TopStoriesResponse.Story oldStory = oldList.get(oldItemPosition);
        TopStoriesResponse.Story newStory = newList.get(newItemPosition);

        return oldStory.getTitle().equals(newStory.getTitle()) &&
                oldStory.getStoryAbstract().equals(newStory.getStoryAbstract()) &&
                oldStory.getUrl().equals(newStory.getUrl()) &&
                oldStory.getPublishedDate().equals(newStory.getPublishedDate());
    }
}

