package com.project.mondo.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TopStoriesResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("results")
    private List<Story> stories;

    public TopStoriesResponse(String status, List<Story> stories) {
        this.status = status;
        this.stories = stories;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }


}
