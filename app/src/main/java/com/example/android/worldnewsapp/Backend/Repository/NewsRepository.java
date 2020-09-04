package com.example.android.worldnewsapp.Backend.Repository;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.worldnewsapp.Backend.Database.DataAccessObject.TheNewsDao;
import com.example.android.worldnewsapp.Backend.Database.Model.DatabaseDetails;
import com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal;
import com.example.android.worldnewsapp.Backend.Database.NewsDatabase;
import com.example.android.worldnewsapp.Model.News;
import com.example.android.worldnewsapp.Model.NewsResponse;
import com.example.android.worldnewsapp.Model.Source;
import com.example.android.worldnewsapp.R;
import com.example.android.worldnewsapp.Rest.ApiClient;
import com.example.android.worldnewsapp.Rest.ApiInterface;
import com.example.android.worldnewsapp.Utils.ConnectionDetector;

import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    //private final static String country = "gb";
    //private final static String category = "sports";
    private final static String API_KEY = DatabaseDetails.API_KEY;
    private static final String TAG = NewsRepository.class.getSimpleName();

    // Connection detector class
    ConnectionDetector cd;
    Boolean isInternetPresent = false;

    // Alert Dialog Manager
    private TheNewsDao newsDao;
    private LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> allNews;
    private LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> topTenNews;

    private LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> allGeneral;
    private LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> allEntertainment;
    private LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> allTopBusiness;
    private LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> allTopSport;


    public NewsRepository(Application application, String category, String country) {
        NewsDatabase newsDatabase = NewsDatabase.getInstance(application);
        new Thread(() -> {
            newsDao = newsDatabase.newsDao();
            allNews = newsDao.getCategoryNewsArticles(category);
            topTenNews = newsDao.getTopTenCategoryNewsArticles(category);
            /*if (webServiceNews == null) {
                webServiceNews = getNewsFromWebService();
            }*/

            if (allNews == null) {
                initData(category, country);
            }

        }).start();
    }

    public NewsRepository(Application application) {
        NewsDatabase newsDatabase = NewsDatabase.getInstance(application);
        new Thread(() -> {
            newsDao = newsDatabase.newsDao();
            allGeneral = newsDao.getCategoryNewsArticles("general");
            allEntertainment = newsDao.getCategoryNewsArticles("entertainment");
            allTopBusiness = newsDao.getTopTenCategoryNewsArticles("business");
            allTopSport = newsDao.getTopTenCategoryNewsArticles("sports");
            /*if (webServiceNews == null) {
                webServiceNews = getNewsFromWebService();
            }*/

        }).start();
    }

    public NewsRepository() {
    }


    public void initData(String category, String country) {
        clearAllArticles(category);
        insertAllArticles(category, country);
    }

    public void initData(Map<String, String> input) {
        new Thread(() -> {
            for (Map.Entry<String, String> entry : input.entrySet()) {
                String category = entry.getKey();
                String country = entry.getValue();
                clearAllArticles(category);
                insertAllArticles(category, country);
            }
        }).start();
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

    public LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> getAllNews() {
        return allNews;
    }

    public LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> getTopNews() {
        return topTenNews;
    }

    public LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> getGeneralNews() {
        return allGeneral;
    }

    public LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> getEntertainmentNews() {
        return allEntertainment;
    }

    public LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> getTopSportNews() {
        return allTopSport;
    }

    public LiveData<List<com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal>> getTopBusinessNews() {
        return allTopBusiness;
    }


    private void insertAllArticles(String category, String country) {
        new InsertAllArticlesAsyncTask(newsDao, category, country).execute();
    }

    private void clearAllArticles(String category) {
        new ClearAllArticlesAsyncTask(newsDao, category).execute();
    }


    private static class ClearAllArticlesAsyncTask extends AsyncTask<Void, Integer, Void> {
        Context context;
        Dialog dialog;
        ProgressBar progress;
        private TheNewsDao newsDao;
        private String categoryInput;

        private ClearAllArticlesAsyncTask(TheNewsDao newsDao, String categoryInput) {
            this.newsDao = newsDao;
            this.categoryInput = categoryInput;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsDao.clearCategoryArticle(categoryInput);
            return null;
        }

        /*@Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.setCancelable(true);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.progress_dialog);
            progress = dialog.findViewById(R.id.progressBar);

            progress.setProgress(0);

            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.setVisibility(View.GONE);
            dialog.dismiss();
            Toast.makeText(context, "Finished", Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress.setProgress(values[0]);
        }*/

    }

    private static class InsertAllArticlesAsyncTask extends AsyncTask<Void, Integer, Void> {
        Context context;
        Dialog dialog;
        ProgressBar progress;
        private TheNewsDao newsDao;
        private String categoryInput;
        private String countryInput;

        private InsertAllArticlesAsyncTask(TheNewsDao newsDao, String categoryInput, String countryInput) {
            this.newsDao = newsDao;
            this.categoryInput = categoryInput;
            this.countryInput = countryInput;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            new Thread(() -> {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<NewsResponse> call = apiService.getTopHeadlines(countryInput, categoryInput, API_KEY);
                call.enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        int statusCode = response.code();
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            List<News> freshNews = response.body().getArticles();
                            for (News eachNew : freshNews) {
                                new Thread(() -> {
                                    NewsLocal oneAtATime = convertObject(eachNew, categoryInput);
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new Dialog(context);
            dialog.setCancelable(true);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.progress_dialog);
            progress = dialog.findViewById(R.id.progressBar);

            progress.setProgress(0);

            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.setVisibility(View.GONE);
            dialog.dismiss();
            Toast.makeText(context, "Finished", Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress.setProgress(values[0]);
        }
    }

}
