package com.project.mondo.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ArticleSearchResponse {
    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public static class Response {
        @SerializedName("results")
        private List<Article> results;

        public List<Article> getResults() {
            return results;
        }

        public static class Article {
            @SerializedName("webUrl")
            private String webUrl;

            @SerializedName("id")
            private String id;

            @SerializedName("webTitle")
            private String webTitle;

            @SerializedName("sectionName")
            private String sectionName;

            @SerializedName("apiUrl")
            private String apiUrl;

            @SerializedName("fields")
            private Fields fields;

            public String getWebUrl() {
                return webUrl;
            }

            public String getId() {
                return id;
            }

            public String getWebTitle() {
                return webTitle;
            }

            public String getSectionName() {
                return sectionName;
            }

            public String getApiUrl() {
                return apiUrl;
            }

            public Fields getFields() {
                return fields;
            }

            public static class Fields {
                @SerializedName("bodyText")
                private String bodyText;

                @SerializedName("trailText")
                private String trailText;

                public String getBodyText() {
                    return bodyText;
                }

                public String getTrailText() {
                    return trailText;
                }
            }
        }
    }
}
