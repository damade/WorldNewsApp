package com.example.android.worldnewsapp.Fragments.BusinessFragment.BusinessViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BusinessViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private Application mApplication;
    private String mParam;

    public BusinessViewModelFactory(@NonNull Application application) {
        super(application);
        mApplication = application;
        //mParam = param;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new BusinessViewModel(mApplication);
    }
}
