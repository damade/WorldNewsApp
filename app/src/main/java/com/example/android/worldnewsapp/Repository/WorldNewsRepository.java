package com.example.android.worldnewsapp.Repository;

import android.app.Application;
import android.util.Log;

import com.example.android.worldnewsapp.Model.LiveNews;
import com.example.android.worldnewsapp.Model.LiveNewsResponse;
import com.example.android.worldnewsapp.Rest.ApiClient;
import com.example.android.worldnewsapp.Rest.ApiInterface;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorldNewsRepository {
    private final static String country = "gb";
    private final static String category = "sports";
    private final static String API_KEY = "7bf5a5d4b62e4d1c939e97ebf66167c4";
    private static final String TAG = WorldNewsRepository.class.getSimpleName();
    private LiveData<List<LiveNews>> allNews;

    public WorldNewsRepository(Application application) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        if (API_KEY.isEmpty()) {
            return;
        } else {
            Call<LiveNewsResponse> call = apiService.getTopHeadlines(country, category, API_KEY);
            call.enqueue(new Callback<LiveNewsResponse>() {
                @Override
                public void onResponse(Call<LiveNewsResponse> call, Response<LiveNewsResponse> response) {
                    int statusCode = response.code();
                    allNews = (LiveData<List<LiveNews>>) response.body().getArticles();
                }

                @Override
                public void onFailure(Call<LiveNewsResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        }
    }

    public LiveData<List<LiveNews>> getAllNews() {
        return allNews;
    }

}
