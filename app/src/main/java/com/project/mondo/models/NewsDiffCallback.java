package com.project.mondo.models;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class NewsDiffCallback extends DiffUtil.Callback {

    private final List<ArticleSearchResponse.Response.Article> oldList;
    private final List<ArticleSearchResponse.Response.Article> newList;

    public NewsDiffCallback(List<ArticleSearchResponse.Response.Article> oldList, List<ArticleSearchResponse.Response.Article> newList) {
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
        // Assuming 'id' uniquely identifies each article
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ArticleSearchResponse.Response.Article oldArticle = oldList.get(oldItemPosition);
        ArticleSearchResponse.Response.Article newArticle = newList.get(newItemPosition);

        return oldArticle.getWebTitle().equals(newArticle.getWebTitle()) &&
                oldArticle.getFields().getTrailText().equals(newArticle.getFields().getTrailText()) &&
                oldArticle.getSectionName().equals(newArticle.getSectionName()) &&
                oldArticle.getWebUrl().equals(newArticle.getWebUrl());
    }
}
