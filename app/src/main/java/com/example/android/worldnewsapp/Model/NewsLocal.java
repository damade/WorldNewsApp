package com.example.android.worldnewsapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsLocal {
    @SerializedName("source")
    @Expose
    private Source sources;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;
    @SerializedName("content")
    @Expose
    private String content;

    public NewsLocal(Source sources, String author, String description,
                     String url, String urlToImage, String title, String publishedAt, String content) {
        this.sources = sources;
        this.author = author;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.title = title;
        this.publishedAt = publishedAt;

        this.content = content;
    }

    public Source getSources() {
        return sources;
    }

    public void setSources(Source sources) {
        this.sources = sources;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
