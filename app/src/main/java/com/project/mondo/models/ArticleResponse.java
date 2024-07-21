package com.project.mondo.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse {

    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        @SerializedName("results")
        private List<Article> results;

        public List<Article> getResults() {
            return results;
        }

        public void setResults(List<Article> results) {
            this.results = results;
        }
    }
}
