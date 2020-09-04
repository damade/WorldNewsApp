package com.example.android.worldnewsapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.android.worldnewsapp.Adapter.NewsAdapter;
import com.example.android.worldnewsapp.Backend.Database.Model.DatabaseDetails;
import com.example.android.worldnewsapp.Fragments.BusinessFragment.BusinessFragment;
import com.example.android.worldnewsapp.Fragments.HomeFragment.HomeFragment;
import com.example.android.worldnewsapp.Fragments.HomeFragment.HomeViewModel.HomeViewModel;
import com.example.android.worldnewsapp.Fragments.HomeFragment.HomeViewModel.HomeViewModelFactory;
import com.example.android.worldnewsapp.Fragments.OtherFragment.OtherFragment;
import com.example.android.worldnewsapp.Fragments.SearchFragment.SearchFragment;
import com.example.android.worldnewsapp.Fragments.SportFragment.SportFragment;
import com.example.android.worldnewsapp.Model.LiveNewsResponse;
import com.example.android.worldnewsapp.Model.News;
import com.example.android.worldnewsapp.Model.NewsResponse;
import com.example.android.worldnewsapp.R;
import com.example.android.worldnewsapp.Rest.ApiClient;
import com.example.android.worldnewsapp.Rest.ApiInterface;
import com.example.android.worldnewsapp.Utils.AlertDialogManager;
import com.example.android.worldnewsapp.Utils.BottomNavigationBehaviour;
import com.example.android.worldnewsapp.Utils.ConnectionDetector;
import com.example.android.worldnewsapp.ViewModel.WorldNewsViewModel;
import com.example.android.worldnewsapp.ViewModelFactory.WorldNewsViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
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

    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;

    private LiveData<List<LiveNewsResponse>> theNewsList;

    private RecyclerView recyclerView;
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    private HomeViewModel homeViewModel;
    private HomeViewModelFactory homeViewModelFactory;


    private ActionBar toolbar;

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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Home");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_search:
                    toolbar.setTitle("Search");
                    fragment = new SearchFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_business:
                    toolbar.setTitle("Business");
                    fragment = new BusinessFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_sports:
                    toolbar.setTitle("Sport");
                    fragment = new SportFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_others:
                    toolbar.setTitle("Others");
                    fragment = new OtherFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        /*final LiveNewsAdapter liveNewsAdapter = new LiveNewsAdapter();
        recyclerView.setAdapter(liveNewsAdapter);
        worldNewsViewModel.getAllNews().observe(this, liveNews -> liveNewsAdapter.submitList(liveNews));
        liveNewsAdapter.setOnItemClickListener(new LiveNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NewsLocal newsLocal) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra(WebActivity.NEWS_URL, newsLocal.getUrl());
                startActivity(intent);
            }
        });*/

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
    protected void onRestart() {
        super.onRestart();
        Log.d(MainActivity.class.getSimpleName(), "Does it display the note");

        /*final LiveNewsAdapter liveNewsAdapter = new LiveNewsAdapter();
        recyclerView.setAdapter(liveNewsAdapter);
        worldNewsViewModel.getAllNews().observe(this, liveNews -> liveNewsAdapter.submitList(liveNews));
        liveNewsAdapter.setOnItemClickListener(new LiveNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NewsLocal newsLocal) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra(WebActivity.NEWS_URL, newsLocal.getUrl());
                startActivity(intent);
            }
        });*/

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

    public void viewAllNews(View view) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = getSupportActionBar();
        toolbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        toolbar.setCustomView(R.layout.custom_toolbar);

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

        homeViewModelFactory = new HomeViewModelFactory(getApplication());
        homeViewModel = new ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel.class);

        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            homeViewModel.initData();
        }

        BottomNavigationView navigation = findViewById(R.id.bottomBar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehaviour());

        loadFragment(new HomeFragment());

    }
}
