package com.example.android.worldnewsapp.Activity;

import android.app.NotificationManager;
import android.app.job.JobScheduler;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.worldnewsapp.Adapter.LiveNewsAdapter;
import com.example.android.worldnewsapp.Database.Model.NewsLocal;
import com.example.android.worldnewsapp.Model.LiveNewsResponse;
import com.example.android.worldnewsapp.Notifications.NotificationReceiver;
import com.example.android.worldnewsapp.R;
import com.example.android.worldnewsapp.ViewModel.WorldNewsViewModel;
import com.example.android.worldnewsapp.ViewModelFactory.WorldNewsViewModelFactory;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String country = "gb";
    private final static String category = "sports";
    private final static String API_KEY = "7bf5a5d4b62e4d1c939e97ebf66167c4";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);

        //createNotificationChannel();

        // Register the broadcast receiver to receive the update action from
        // the notification.


        recyclerView = findViewById(R.id.news_recycler_view);
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
        });


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

    /*private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/
}
