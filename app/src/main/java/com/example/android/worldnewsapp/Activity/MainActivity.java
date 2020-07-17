package com.example.android.worldnewsapp.Activity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.android.worldnewsapp.Adapter.LiveNewsAdapter;
import com.example.android.worldnewsapp.Model.LiveNewsResponse;
import com.example.android.worldnewsapp.Model.NewsLocal;
import com.example.android.worldnewsapp.R;
import com.example.android.worldnewsapp.ViewModel.WorldNewsViewModel;
import com.example.android.worldnewsapp.ViewModelFactory.WorldNewsViewModelFactory;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String country = "gb";
    private final static String category = "sports";
    private final static String API_KEY = "7bf5a5d4b62e4d1c939e97ebf66167c4";

    private WorldNewsViewModel worldNewsViewModel;
    private WorldNewsViewModelFactory worldNewsViewModelFactory;
    private LiveData<List<LiveNewsResponse>> theNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from newsapi.org first!", Toast.LENGTH_LONG).show();
            return;
        }
        final RecyclerView recyclerView = findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final LiveNewsAdapter liveNewsAdapter = new LiveNewsAdapter();
        recyclerView.setAdapter(liveNewsAdapter);

        worldNewsViewModelFactory = new WorldNewsViewModelFactory(getApplication());
        worldNewsViewModel = new ViewModelProvider(this, worldNewsViewModelFactory).get(WorldNewsViewModel.class);

        worldNewsViewModel.initData();
        worldNewsViewModel.getAllNews().observe(this, liveNews -> liveNewsAdapter.submitList((List<NewsLocal>) liveNews));
        /*worldNewsViewModel.getAllNews().observe(this, new Observer<List<LiveNews>>() {
            @Override
            public void onChanged(List<LiveNews> liveNews) {
                liveNewsAdapter.submitList(liveNews);
            }
        });*/



        /*final RecyclerView recyclerView = findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<NewsResponse> call = apiService.getTopHeadlines(country,category,API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                int statusCode = response.code();
                List<News> news= response.body().getArticles();
                recyclerView.setAdapter(new NewsAdapter(news, R.layout.list_item_news, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });*/
    }
}
