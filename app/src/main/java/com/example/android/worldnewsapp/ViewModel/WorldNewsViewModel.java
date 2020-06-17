package com.example.android.worldnewsapp.ViewModel;

import android.app.Application;

import com.example.android.worldnewsapp.Model.LiveNews;
import com.example.android.worldnewsapp.Repository.WorldNewsRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WorldNewsViewModel extends AndroidViewModel {
    private WorldNewsRepository repository;
    private LiveData<List<LiveNews>> allNews;

    public WorldNewsViewModel(@NonNull Application application) {
        super(application);
        repository = new WorldNewsRepository(application);
        allNews = repository.getAllNews();
    }

    public LiveData<List<LiveNews>> getAllNews() {
        return allNews;
    }
}
