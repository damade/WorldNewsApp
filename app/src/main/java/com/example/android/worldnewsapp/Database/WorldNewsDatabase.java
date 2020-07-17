package com.example.android.worldnewsapp.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.worldnewsapp.Database.DataAccessObjects.NewsDao;
import com.example.android.worldnewsapp.Database.Model.NewsLocal;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = NewsLocal.class, version = 1, exportSchema = false)
public abstract class WorldNewsDatabase extends RoomDatabase {

    private static WorldNewsDatabase instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static synchronized WorldNewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WorldNewsDatabase.class, "news_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }


    public abstract NewsDao newsDao();

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsDao newsDao;

        private PopulateDbAsyncTask(WorldNewsDatabase db) {
            newsDao = db.newsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
