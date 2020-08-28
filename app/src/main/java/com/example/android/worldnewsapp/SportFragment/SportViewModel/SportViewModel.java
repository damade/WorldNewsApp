package com.example.android.worldnewsapp.SportFragment.SportViewModel;

import android.app.Application;

import com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal;
import com.example.android.worldnewsapp.Backend.Repository.NewsRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SportViewModel extends AndroidViewModel {
    private final static String country = "gb";
    private final static String category = "sports";
    private NewsRepository repository;
    private LiveData<List<NewsLocal>> allNews;

    public SportViewModel(@NonNull Application application) {
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

