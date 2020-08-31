package com.example.android.worldnewsapp.Activity;

import android.app.NotificationManager;
import android.app.job.JobScheduler;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.android.worldnewsapp.Adapter.LiveNewsAdapter;
import com.example.android.worldnewsapp.Adapter.NewsAdapter;
import com.example.android.worldnewsapp.Backend.Database.Model.DatabaseDetails;
import com.example.android.worldnewsapp.Database.Model.NewsLocal;
import com.example.android.worldnewsapp.Fragments.HomeFragment;
import com.example.android.worldnewsapp.Fragments.SearchFragment.SearchFragment;
import com.example.android.worldnewsapp.Model.LiveNewsResponse;
import com.example.android.worldnewsapp.Model.News;
import com.example.android.worldnewsapp.Model.NewsResponse;
import com.example.android.worldnewsapp.Notifications.NotificationReceiver;
import com.example.android.worldnewsapp.R;
import com.example.android.worldnewsapp.Rest.ApiClient;
import com.example.android.worldnewsapp.Rest.ApiInterface;
import com.example.android.worldnewsapp.ViewModel.WorldNewsViewModel;
import com.example.android.worldnewsapp.ViewModelFactory.WorldNewsViewModelFactory;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String country = "gb";
    private final static String category = "sports";
    private final static String API_KEY = DatabaseDetails.API_KEY;

    private WorldNewsViewModel worldNewsViewModel;
    private WorldNewsViewModelFactory worldNewsViewModelFactory;
    private LiveData<List<LiveNewsResponse>> theNewsList;
    // Constants for the notification actions buttons.
    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.android.worldnewsapp.ACTION_UPDATE_NOTIFICATION";
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    // Notification ID.
    private static final int NOTIFICATION_ID = 0;
    private static final int JOB_ID = 0;
    private RecyclerView recyclerView;
    private NotificationManager mNotifyManager;
    private NotificationReceiver mReceiver = new NotificationReceiver();
    private JobScheduler mScheduler;

    private static String getCategory(String categoryInput) {
        String categoryOutput = "";
        switch (categoryInput) {
            case "Business":
                categoryOutput = "business";
                break;
            case "Entertainment":
                categoryOutput = "entertainment";
                break;
            case "Health":
                categoryOutput = "health";
                break;
            case "Science":
                categoryOutput = "science";
                break;
            case "Sport":
                categoryOutput = "sports";
                break;
            case "Technology":
                categoryOutput = "technology";
                break;
            default:
                categoryOutput = "general";
                break;
        }
        return categoryOutput;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final LiveNewsAdapter liveNewsAdapter = new LiveNewsAdapter();
        recyclerView.setAdapter(liveNewsAdapter);
        worldNewsViewModel.getAllNews().observe(this, liveNews -> liveNewsAdapter.submitList(liveNews));
        liveNewsAdapter.setOnItemClickListener(new LiveNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NewsLocal newsLocal) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra(WebActivity.NEWS_URL, newsLocal.getUrl());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(MainActivity.class.getSimpleName(), "Does it display the note");

        final LiveNewsAdapter liveNewsAdapter = new LiveNewsAdapter();
        recyclerView.setAdapter(liveNewsAdapter);
        worldNewsViewModel.getAllNews().observe(this, liveNews -> liveNewsAdapter.submitList(liveNews));
        liveNewsAdapter.setOnItemClickListener(new LiveNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NewsLocal newsLocal) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra(WebActivity.NEWS_URL, newsLocal.getUrl());
                startActivity(intent);
            }
        });

    }

    private static String getCountryCode(String countryInput) {
        String countryIsoCode = "";
        switch (countryInput) {
            case "China":
                countryIsoCode = "cn";
                break;
            case "Usa":
                countryIsoCode = "us";
                break;
            case "United Kingdom":
                countryIsoCode = "gb";
                break;
            case "Italy":
                countryIsoCode = "it";
                break;
            case "Germany":
                countryIsoCode = "de";
                break;
            case "France":
                countryIsoCode = "fr";
                break;
            default:
                countryIsoCode = "us";
                break;
        }
        return countryIsoCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);

        //createNotificationChannel();

        // Register the broadcast receiver to receive the update action from
        // the notification.


        /*recyclerView = findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        worldNewsViewModelFactory = new WorldNewsViewModelFactory(getApplication());
        worldNewsViewModel = new ViewModelProvider(this, worldNewsViewModelFactory).get(WorldNewsViewModel.class);

        final LiveNewsAdapter liveNewsAdapter = new LiveNewsAdapter();
        recyclerView.setAdapter(liveNewsAdapter);

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> {
            worldNewsViewModel.initData();
            worldNewsViewModel.getAllNews().observe(this, liveNews -> liveNewsAdapter.submitList(liveNews));
            pullToRefresh.setRefreshing(false);
        });

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from newsapi.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        worldNewsViewModel.initData();
        worldNewsViewModel.getAllNews().observe(this, liveNews -> liveNewsAdapter.submitList(liveNews));

        liveNewsAdapter.setOnItemClickListener(new LiveNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NewsLocal newsLocal) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra(WebActivity.NEWS_URL, newsLocal.getUrl());
                startActivity(intent);
            }
        });*/

        loadFragment(new HomeFragment());

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void searchForNews(View view) {
        EditText source = findViewById(R.id.commonSources);
        String searchCountry = getCountryCode(SearchFragment.spinnerCountry.getSelectedItem().toString());
        String searchCategory = getCategory(SearchFragment.spinnerCategory.getSelectedItem().toString());
        final RecyclerView recyclerView = findViewById(R.id.sport_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<NewsResponse> call = apiService.getTopHeadlines(searchCountry, searchCategory, API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                int statusCode = response.code();
                List<News> news = response.body().getArticles();
                NewsAdapter newsAdapter = new NewsAdapter(news, R.layout.list_item_news, getApplicationContext());
                recyclerView.setAdapter(newsAdapter);
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
