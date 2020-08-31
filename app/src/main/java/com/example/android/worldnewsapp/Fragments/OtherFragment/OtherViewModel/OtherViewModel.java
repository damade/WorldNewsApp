package com.example.android.worldnewsapp.Fragments.OtherFragment.OtherViewModel;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal;
import com.example.android.worldnewsapp.Backend.Repository.NewsRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;

public class OtherViewModel extends AndroidViewModel {
    private final static String country = "us";
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
    String category = sharedPreferences.getString("category", "technology");
    private NewsRepository repository;
    private LiveData<List<NewsLocal>> allNews;
    //private final static String category = "business";

    public OtherViewModel(@NonNull Application application) {
        super(application);
        repository = new NewsRepository(application, category.toLowerCase(), country);
        allNews = repository.getAllNews();
    }

    public void initData() {
        /*if (allNews != null) {
            return;
        }*/
        repository = new NewsRepository(getApplication(), category.toLowerCase(), country);
        allNews = repository.getAllNews();
    }


    public LiveData<List<NewsLocal>> getAllNews() {
        return allNews;
    }
}

