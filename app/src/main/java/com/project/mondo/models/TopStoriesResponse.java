package com.project.mondo.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TopStoriesResponse implements Parcelable {
    @SerializedName("status")
    private String status;

    @SerializedName("results")
    private List<Story> stories;

    public TopStoriesResponse(String status, List<Story> stories) {
        this.status = status;
        this.stories = stories;
    }

    protected TopStoriesResponse(Parcel in) {
        status = in.readString();
        stories = in.createTypedArrayList(Story.CREATOR);
    }

    public static final Creator<TopStoriesResponse> CREATOR = new Creator<TopStoriesResponse>() {
        @Override
        public TopStoriesResponse createFromParcel(Parcel in) {
            return new TopStoriesResponse(in);
        }

        @Override
        public TopStoriesResponse[] newArray(int size) {
            return new TopStoriesResponse[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeTypedList(stories);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public static class Story implements Parcelable {
        private static final long serialVersionUID = 1L;

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

        protected Story(Parcel in) {
            section = in.readString();
            subsection = in.readString();
            title = in.readString();
            storyAbstract = in.readString();
            url = in.readString();
            uri = in.readString();
            byline = in.readString();
            itemType = in.readString();
            updatedDate = in.readString();
            createdDate = in.readString();
            publishedDate = in.readString();
            materialTypeFacet = in.readString();
            kicker = in.readString();
            desFacet = in.createStringArrayList();
            orgFacet = in.createStringArrayList();
            perFacet = in.createStringArrayList();
            geoFacet = in.createStringArrayList();
            multimedia = in.createTypedArrayList(Multimedia.CREATOR);
            shortUrl = in.readString();
        }

        public static final Creator<Story> CREATOR = new Creator<Story>() {
            @Override
            public Story createFromParcel(Parcel in) {
                return new Story(in);
            }

            @Override
            public Story[] newArray(int size) {
                return new Story[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(section);
            dest.writeString(subsection);
            dest.writeString(title);
            dest.writeString(storyAbstract);
            dest.writeString(url);
            dest.writeString(uri);
            dest.writeString(byline);
            dest.writeString(itemType);
            dest.writeString(updatedDate);
            dest.writeString(createdDate);
            dest.writeString(publishedDate);
            dest.writeString(materialTypeFacet);
            dest.writeString(kicker);
            dest.writeStringList(desFacet);
            dest.writeStringList(orgFacet);
            dest.writeStringList(perFacet);
            dest.writeStringList(geoFacet);
            dest.writeTypedList(multimedia);
            dest.writeString(shortUrl);
        }

        @Override
        public int describeContents() {
            return 0;
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

        public String getId() {
            return uri;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Story story = (Story) obj;
            return uri.equals(story.uri);
        }

        @Override
        public int hashCode() {
            return uri.hashCode();
        }
    }

    public static class Multimedia implements Parcelable {
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

        protected Multimedia(Parcel in) {
            url = in.readString();
            format = in.readString();
            height = in.readInt();
            width = in.readInt();
        }

        public static final Creator<Multimedia> CREATOR = new Creator<Multimedia>() {
            @Override
            public Multimedia createFromParcel(Parcel in) {
                return new Multimedia(in);
            }

            @Override
            public Multimedia[] newArray(int size) {
                return new Multimedia[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(url);
            dest.writeString(format);
            dest.writeInt(height);
            dest.writeInt(width);
        }

        @Override
        public int describeContents() {
            return 0;
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