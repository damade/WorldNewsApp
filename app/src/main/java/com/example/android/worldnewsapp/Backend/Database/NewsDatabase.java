package com.example.android.worldnewsapp.Backend.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.worldnewsapp.Backend.Database.DataAccessObject.TheNewsDao;
import com.example.android.worldnewsapp.Backend.Database.Model.DatabaseDetails;
import com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = NewsLocal.class, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

    private static NewsDatabase instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static synchronized NewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NewsDatabase.class, DatabaseDetails.databaseName)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }


    public abstract TheNewsDao newsDao();

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TheNewsDao newsDao;

        private PopulateDbAsyncTask(NewsDatabase db) {
            newsDao = db.newsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}

