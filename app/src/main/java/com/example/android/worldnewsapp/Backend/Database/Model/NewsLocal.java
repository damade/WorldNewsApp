package com.example.android.worldnewsapp.Backend.Database.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = DatabaseDetails.tableName)
public class NewsLocal {
    @PrimaryKey(autoGenerate = true)
    private int newsId;

    @Ignore
    @ColumnInfo(name = DatabaseDetails.newsColumnSource)
    private com.example.android.worldnewsapp.Backend.Database.Model.Source sources;

    @ColumnInfo(name = DatabaseDetails.newsColumnAuthor)
    private String author;

    @ColumnInfo(name = DatabaseDetails.newsColumnTitle)
    private String title;

    @ColumnInfo(name = DatabaseDetails.newsColumnDescription)
    private String description;

    @ColumnInfo(name = DatabaseDetails.newsColumnUrl)
    private String url;

    @ColumnInfo(name = DatabaseDetails.newsColumnUrlToImage)
    private String urlToImage;

    @ColumnInfo(name = DatabaseDetails.newsColumnPublished)
    private String publishedAt;

    @ColumnInfo(name = DatabaseDetails.newsColumnContent)
    private String content;

    @ColumnInfo(name = DatabaseDetails.newsColumnCategory)
    private String category;

    public NewsLocal() {
    }

    public NewsLocal(com.example.android.worldnewsapp.Backend.Database.Model.Source sources, String author, String description,
                     String url, String urlToImage, String title, String publishedAt, String content, String category) {
        this.sources = sources;
        this.author = author;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.title = title;
        this.publishedAt = publishedAt;
        this.content = content;
        this.category = category;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public com.example.android.worldnewsapp.Backend.Database.Model.Source getSources() {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String content) {
        this.category = category;
    }
}
