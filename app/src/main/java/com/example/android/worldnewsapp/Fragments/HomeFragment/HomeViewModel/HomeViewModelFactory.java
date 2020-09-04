package com.example.android.worldnewsapp.Fragments.HomeFragment.HomeViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HomeViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private Application mApplication;
    private String mParam;

    public HomeViewModelFactory(@NonNull Application application) {
        super(application);
        mApplication = application;
        //mParam = param;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new HomeViewModel(mApplication);
    }
}
