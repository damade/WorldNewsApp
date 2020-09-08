package com.example.android.worldnewsapp.Fragments.HomeFragment.HomeViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal;
import com.example.android.worldnewsapp.Backend.Repository.NewsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends AndroidViewModel {
    private NewsRepository repository;
    private LiveData<List<NewsLocal>> allGeneralNews;
    private LiveData<List<NewsLocal>> allEntertainmentNews;
    private LiveData<List<NewsLocal>> allTopBusinessNews;
    private LiveData<List<NewsLocal>> allTopSportNews;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new NewsRepository(application);
        allGeneralNews = repository.getGeneralNews();
        allEntertainmentNews = repository.getEntertainmentNews();
        allTopBusinessNews = repository.getTopBusinessNews();
        allTopSportNews = repository.getTopSportNews();
    }

    public void initData() {
        /*if (allNews != null) {
            return;
        }*/
        //Map<category, country>
        Map<String, String> query = new HashMap<String, String>();
        query.put("general", "us");
        query.put("entertainment", "us");
        query.put("business", "us");
        query.put("sports", "gb");
        NewsRepository repository = new NewsRepository();
        repository.initData(query,getApplication());
    }


    public LiveData<List<NewsLocal>> getAllGeneralNews() {
        return allGeneralNews;
    }

    public LiveData<List<NewsLocal>> getAllEntertainmentNews() {
        return allEntertainmentNews;
    }

    public LiveData<List<NewsLocal>> getAllTopBusinessNews() {
        return allTopBusinessNews;
    }

    public LiveData<List<NewsLocal>> getAllTopSportNews() {
        return allTopSportNews;
    }
}
