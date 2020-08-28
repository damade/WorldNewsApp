package com.example.android.worldnewsapp.SportFragment.SportViewModel;

import android.app.Application;

import com.example.android.worldnewsapp.ViewModel.WorldNewsViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SportViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private Application mApplication;
    private String mParam;

    public SportViewModelFactory(@NonNull Application application) {
        super(application);
        mApplication = application;
        //mParam = param;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new WorldNewsViewModel(mApplication);
    }
}

