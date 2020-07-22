package com.example.android.worldnewsapp.Database.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_table")
public class NewsLocal {

    @PrimaryKey(autoGenerate = true)
    private int newsId;

    @Ignore
    @ColumnInfo(name = "news_sources")
    private Source sources;

    @ColumnInfo(name = "news_author")
    private String author;

    @ColumnInfo(name = "news_title")
    private String title;

    @ColumnInfo(name = "news_description")
    private String description;

    @ColumnInfo(name = "news_url")
    private String url;

    @ColumnInfo(name = "news_url_to_image")
    private String urlToImage;

    @ColumnInfo(name = "news_published_at")
    private String publishedAt;

    @ColumnInfo(name = "news_content")
    private String content;

    public NewsLocal() {
    }

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

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
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
