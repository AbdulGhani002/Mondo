package com.project.mondo.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Article implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("webTitle")
    private String title;

    @SerializedName("webUrl")
    private String url;

    @SerializedName("apiUrl")
    private String apiUrl;

    @SerializedName("fields")
    private Fields fields;

    @SerializedName("tags")
    private List<Tag> tags;

    @SerializedName("sectionName")
    private String sectionName;

    @SerializedName("webPublicationDate")
    private String publicationDate;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", fields=" + fields +
                ", tags=" + tags +
                ", sectionName='" + sectionName + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                '}';
    }

    public static class Fields implements Serializable {
        @SerializedName("body")
        private String body;

        @SerializedName("trailText")
        private String trailText;

        // Getters and Setters
        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTrailText() {
            return trailText;
        }

        public void setTrailText(String trailText) {
            this.trailText = trailText;
        }

        @Override
        public String toString() {
            return "Fields{" +
                    "body='" + body + '\'' +
                    ", trailText='" + trailText + '\'' +
                    '}';
        }
    }

    public static class Tag implements Serializable {
        @SerializedName("id")
        private String id;

        @SerializedName("type")
        private String type;

        @SerializedName("webTitle")
        private String webTitle;

        @SerializedName("webUrl")
        private String webUrl;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWebTitle() {
            return webTitle;
        }

        public void setWebTitle(String webTitle) {
            this.webTitle = webTitle;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        @Override
        public String toString() {
            return "Tag{" +
                    "id='" + id + '\'' +
                    ", type='" + type + '\'' +
                    ", webTitle='" + webTitle + '\'' +
                    ", webUrl='" + webUrl + '\'' +
                    '}';
        }
    }
}
