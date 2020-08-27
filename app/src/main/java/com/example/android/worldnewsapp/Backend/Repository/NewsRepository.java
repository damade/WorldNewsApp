package com.example.android.worldnewsapp.Backend.Repository;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.worldnewsapp.Backend.Database.DataAccessObject.TheNewsDao;
import com.example.android.worldnewsapp.Backend.Database.Model.DatabaseDetails;
import com.example.android.worldnewsapp.Backend.Database.NewsDatabase;
import com.example.android.worldnewsapp.Database.DataAccessObjects.NewsDao;
import com.example.android.worldnewsapp.Database.Model.NewsLocal;
import com.example.android.worldnewsapp.Model.News;
import com.example.android.worldnewsapp.Model.NewsResponse;
import com.example.android.worldnewsapp.Model.Source;
import com.example.android.worldnewsapp.Repository.WorldNewsRepository;
import com.example.android.worldnewsapp.Rest.ApiClient;
import com.example.android.worldnewsapp.Rest.ApiInterface;
import com.example.android.worldnewsapp.Utils.ConnectionDetector;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final static String country = "gb";
    private final static String category = "sports";
    private final static String API_KEY = DatabaseDetails.API_KEY;
    private static final String TAG = NewsRepository.class.getSimpleName();

    // Connection detector class
    ConnectionDetector cd;
    Boolean isInternetPresent = false;

    // Alert Dialog Manager
    private TheNewsDao newsDao;
    private LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> allNews;


    public NewsRepository(Application application) {
        NewsDatabase newsDatabase = NewsDatabase.getInstance(application);
        newsDao = newsDatabase.newsDao();
        //allNews = newsDao.getNewsArticles();
        /*if (webServiceNews == null) {
            webServiceNews = getNewsFromWebService();
        }*/
        if (CheckConnectivity(application)) {
            clearAllArticles();
            insertAllArticles();
        } else {
            if (allNews == null) {
                clearAllArticles();
                insertAllArticles();
            }
        }
        /*else {
            //cd = new ConnectionDetector(MainActivity.);
            // Check if Internet present
            //isInternetPresent = cd.isConnectingToInternet();

        }*/

    }

    private static com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal convertObject(News theNews, String categoryInput) {

        Source sources = theNews.getSources();
        com.example.android.worldnewsapp.Backend.Database.Model.Source newSource =
                new com.example.android.worldnewsapp.Backend.Database.Model.Source(sources.getId(), sources.getName());
        String author = theNews.getAuthor();
        String title = theNews.getTitle();
        String description = theNews.getDescription();
        String url = theNews.getUrl();
        String urlToImage = theNews.getUrlToImage();
        String publishedAt = theNews.getPublishedAt();
        String content = theNews.getContent();
        com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal newsLocal =
                new com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal
                        (newSource, author, description, url, urlToImage, title, publishedAt, content, categoryInput);

        return newsLocal;
    }

    private static boolean CheckConnectivity(Application application) {
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager) application.
                getBaseContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        return connected;
    }

    public LiveData<List<com.example.android.worldnewsapp.Database.Model.NewsLocal>> getAllNews() {
        return allNews;
    }

    public void insertArticles(List<com.example.android.worldnewsapp.Database.Model.NewsLocal> news) {
        new WorldNewsRepository.InsertArticlesAsyncTask(newsDao).execute(news);
    }

    public void clearAllArticles() {
        new WorldNewsRepository.ClearAllArticlesAsyncTask(newsDao).execute();
    }

    private List<com.example.android.worldnewsapp.Database.Model.NewsLocal> getNewsFromWebService() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<NewsResponse> call = apiService.getTopHeadlines(country, category, API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                int statusCode = response.code();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    freshNews = response.body().getArticles();
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                freshNews = null;
            }
        });
        return (List<com.example.android.worldnewsapp.Database.Model.NewsLocal>) (com.example.android.worldnewsapp.Model.NewsLocal) freshNews;
    }

    public void insertAllArticles() {
        new WorldNewsRepository.InsertAllArticlesAsyncTask(newsDao).execute();
    }

    private static class InsertArticlesAsyncTask extends AsyncTask<List<NewsLocal>, Void, Void> {
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

    private static class InsertAllArticlesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsDao newsDao;

        private InsertAllArticlesAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            new Thread(() -> {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<NewsResponse> call = apiService.getTopHeadlines(country, category, API_KEY);
                call.enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        int statusCode = response.code();
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            List<News> freshNews = response.body().getArticles();
                            for (News eachNew : freshNews) {
                                new Thread(() -> {
                                    com.example.android.worldnewsapp.Database.Model.NewsLocal oneAtATime = convertObject(eachNew);
                                    newsDao.insert(oneAtATime);
                                }).start();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, t.toString());

                    }
                });
            }).start();
            return null;
        }
    }

}
