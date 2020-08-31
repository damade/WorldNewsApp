package com.example.android.worldnewsapp.Fragments.BusinessFragment.BusinessViewModel;

import android.app.Application;

import com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal;
import com.example.android.worldnewsapp.Backend.Repository.NewsRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BusinessViewModel extends AndroidViewModel {
    private final static String country = "us";
    private LiveData<List<NewsLocal>> allNews;
    private final static String category = "business";
    private NewsRepository repository;

    public BusinessViewModel(@NonNull Application application) {
        super(application);
        repository = new NewsRepository(application, category, country);
        allNews = repository.getAllNews();
    }

    public void initData() {
        /*if (allNews != null) {
            return;
        }*/
        repository = new NewsRepository(getApplication(), category, country);
        allNews = repository.getAllNews();
    }


    public LiveData<List<NewsLocal>> getAllNews() {
        return allNews;
    }
}

