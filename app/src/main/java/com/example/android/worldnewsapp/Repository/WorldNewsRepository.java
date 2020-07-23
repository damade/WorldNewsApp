package com.example.android.worldnewsapp.Repository;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.worldnewsapp.Database.DataAccessObjects.NewsDao;
import com.example.android.worldnewsapp.Database.WorldNewsDatabase;
import com.example.android.worldnewsapp.Model.News;
import com.example.android.worldnewsapp.Model.NewsLocal;
import com.example.android.worldnewsapp.Model.NewsResponse;
import com.example.android.worldnewsapp.Model.Source;
import com.example.android.worldnewsapp.Rest.ApiClient;
import com.example.android.worldnewsapp.Rest.ApiInterface;
import com.example.android.worldnewsapp.Utils.ConnectionDetector;

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

    // Connection detector class
    ConnectionDetector cd;
    Boolean isInternetPresent = false;

    // Alert Dialog Manager

    final MutableLiveData<List<NewsLocal>> data = new MutableLiveData<>();
    private NewsDao newsDao;
    private LiveData<List<com.example.android.worldnewsapp.Database.Model.NewsLocal>> allNews;
    private List<News> freshNews;
    private List<com.example.android.worldnewsapp.Database.Model.NewsLocal> webServiceNews;

    public WorldNewsRepository(Application application) {
        WorldNewsDatabase newsDatabase = WorldNewsDatabase.getInstance(application);
        newsDao = newsDatabase.newsDao();
        webServiceNews = getNewsFromWebService();
        allNews = newsDao.getNewsArticles();
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

    public LiveData<List<com.example.android.worldnewsapp.Database.Model.NewsLocal>> getAllNews() {
        return allNews;
    }

    private static com.example.android.worldnewsapp.Database.Model.NewsLocal convertObject(News theNews) {

        Source sources = theNews.getSources();
        com.example.android.worldnewsapp.Database.Model.Source newSource =
                new com.example.android.worldnewsapp.Database.Model.Source(sources.getId(), sources.getName());
        String author = theNews.getAuthor();
        String title = theNews.getTitle();
        String description = theNews.getDescription();
        String url = theNews.getUrl();
        String urlToImage = theNews.getUrlToImage();
        String publishedAt = theNews.getPublishedAt();
        String content = theNews.getContent();
        com.example.android.worldnewsapp.Database.Model.NewsLocal newsLocal =
                new com.example.android.worldnewsapp.Database.Model.NewsLocal
                        (newSource, author, description, url, urlToImage, title, publishedAt, content);

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

    public void insertArticles(List<com.example.android.worldnewsapp.Database.Model.NewsLocal> news) {
        new InsertArticlesAsyncTask(newsDao).execute(news);
    }

    public void clearAllArticles() {
        new ClearAllArticlesAsyncTask(newsDao).execute();
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
        return (List<com.example.android.worldnewsapp.Database.Model.NewsLocal>) (NewsLocal) freshNews;
    }

    public void insertAllArticles() {
        new InsertAllArticlesAsyncTask(newsDao).execute();
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
