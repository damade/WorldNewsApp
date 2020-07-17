package com.example.android.worldnewsapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.lifecycle.LiveData;

public class LiveNewsResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private int totalResults;
    @SerializedName("articles")
    @Expose
    private LiveData<List<NewsLocal>> articles;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LiveData<List<NewsLocal>> getArticles() {
        return articles;
    }

    public void setArticles(LiveData<List<NewsLocal>> articles) {
        this.articles = articles;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
