package com.example.android.worldnewsapp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.worldnewsapp.Database.DataAccessObjects.NewsDao;
import com.example.android.worldnewsapp.Database.WorldNewsDatabase;
import com.example.android.worldnewsapp.Model.News;
import com.example.android.worldnewsapp.Model.NewsLocal;
import com.example.android.worldnewsapp.Model.NewsResponse;
import com.example.android.worldnewsapp.Rest.ApiClient;
import com.example.android.worldnewsapp.Rest.ApiInterface;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorldNewsRepository {
    private final static String country = "gb";
    private final static String category = "sports";
    private final static String API_KEY = "7bf5a5d4b62e4d1c939e97ebf66167c4";
    private static final String TAG = WorldNewsRepository.class.getSimpleName();

    final MutableLiveData<List<NewsLocal>> data = new MutableLiveData<>();
    private NewsDao newsDao;
    private LiveData<List<com.example.android.worldnewsapp.Database.Model.NewsLocal>> allNews;
    private List<News> freshNews;

    public WorldNewsRepository(Application application) {
        WorldNewsDatabase newsDatabase = WorldNewsDatabase.getInstance(application);
        newsDao = newsDatabase.newsDao();
        allNews = newsDao.getNewsArticles();

    }

    public LiveData<List<com.example.android.worldnewsapp.Database.Model.NewsLocal>> getAllNews() {
        return allNews;
    }

    public List<News> getNewsFromService() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<NewsResponse> call = apiService.getTopHeadlines(country, category, API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                int statusCode = response.code();
                freshNews = response.body().getArticles();
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
        return freshNews;
    }


    public void insertArticles(List<com.example.android.worldnewsapp.Database.Model.NewsLocal> news) {
        new InsertArticlesAsyncTask(newsDao).execute(news);
    }


    public void clearAllArticles() {
        new ClearAllArticlesAsyncTask(newsDao).execute();
    }


    private static class InsertArticlesAsyncTask extends AsyncTask<List<com.example.android.worldnewsapp.Database.Model.NewsLocal>, Void, Void> {
        private NewsDao newsDao;

        private InsertArticlesAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(List<com.example.android.worldnewsapp.Database.Model.NewsLocal>... news) {
            newsDao.insertArticles(news[0]);
            return null;
        }
    }


    private static class ClearAllArticlesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsDao newsDao;

        private ClearAllArticlesAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsDao.clearAllArticles();
            return null;
        }
    }

}
