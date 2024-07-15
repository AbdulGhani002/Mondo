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

    public static class Story {
        @SerializedName("section")
        private String section;

        @SerializedName("subsection")
        private String subsection;

        @SerializedName("title")
        private String title;

        @SerializedName("abstract")
        private String storyAbstract;

        @SerializedName("url")
        private String url;

        @SerializedName("uri")
        private String uri;

        @SerializedName("byline")
        private String byline;

        @SerializedName("item_type")
        private String itemType;

        @SerializedName("updated_date")
        private String updatedDate;

        @SerializedName("created_date")
        private String createdDate;

        @SerializedName("published_date")
        private String publishedDate;

        @SerializedName("material_type_facet")
        private String materialTypeFacet;

        @SerializedName("kicker")
        private String kicker;

        @SerializedName("des_facet")
        private List<String> desFacet;

        @SerializedName("org_facet")
        private List<String> orgFacet;

        @SerializedName("per_facet")
        private List<String> perFacet;

        @SerializedName("geo_facet")
        private List<String> geoFacet;

        @SerializedName("multimedia")
        private List<Multimedia> multimedia;

        @SerializedName("short_url")
        private String shortUrl;

        public Story(String section, String subsection, String title, String storyAbstract,
                     String url, String uri, String byline, String itemType, String updatedDate,
                     String createdDate, String publishedDate, String materialTypeFacet,
                     String kicker, List<String> desFacet, List<String> orgFacet, List<String> perFacet,
                     List<String> geoFacet, List<Multimedia> multimedia, String shortUrl) {
            this.section = section;
            this.subsection = subsection;
            this.title = title;
            this.storyAbstract = storyAbstract;
            this.url = url;
            this.uri = uri;
            this.byline = byline;
            this.itemType = itemType;
            this.updatedDate = updatedDate;
            this.createdDate = createdDate;
            this.publishedDate = publishedDate;
            this.materialTypeFacet = materialTypeFacet;
            this.kicker = kicker;
            this.desFacet = desFacet;
            this.orgFacet = orgFacet;
            this.perFacet = perFacet;
            this.geoFacet = geoFacet;
            this.multimedia = multimedia;
            this.shortUrl = shortUrl;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getSubsection() {
            return subsection;
        }

        public void setSubsection(String subsection) {
            this.subsection = subsection;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStoryAbstract() {
            return storyAbstract;
        }

        public void setStoryAbstract(String storyAbstract) {
            this.storyAbstract = storyAbstract;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getByline() {
            return byline;
        }

        public void setByline(String byline) {
            this.byline = byline;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getMaterialTypeFacet() {
            return materialTypeFacet;
        }

        public void setMaterialTypeFacet(String materialTypeFacet) {
            this.materialTypeFacet = materialTypeFacet;
        }

        public String getKicker() {
            return kicker;
        }

        public void setKicker(String kicker) {
            this.kicker = kicker;
        }
        public List<String> getDesFacet() {
            return desFacet;
        }

        public void setDesFacet(List<String> desFacet) {
            this.desFacet = desFacet;
        }

        public List<String> getOrgFacet() {
            return orgFacet;
        }

        public void setOrgFacet(List<String> orgFacet) {
            this.orgFacet = orgFacet;
        }

        public List<String> getPerFacet() {
            return perFacet;
        }

        public void setPerFacet(List<String> perFacet) {
            this.perFacet = perFacet;
        }

        public List<String> getGeoFacet() {
            return geoFacet;
        }

        public void setGeoFacet(List<String> geoFacet) {
            this.geoFacet = geoFacet;
        }

        public List<Multimedia> getMultimedia() {
            return multimedia;
        }

        public void setMultimedia(List<Multimedia> multimedia) {
            this.multimedia = multimedia;
        }

        public String getShortUrl() {
            return shortUrl;
        }

        public void setShortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
        }

        // Add getters and setters for other fields as necessary
    }

    public static class Multimedia {
        @SerializedName("url")
        private String url;

        @SerializedName("format")
        private String format;

        @SerializedName("height")
        private int height;

        @SerializedName("width")
        private int width;

        public Multimedia(String url, String format, int height, int width) {
            this.url = url;
            this.format = format;
            this.height = height;
            this.width = width;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }
}
