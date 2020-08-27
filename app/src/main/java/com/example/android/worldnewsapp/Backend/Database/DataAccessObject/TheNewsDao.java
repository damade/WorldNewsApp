package com.example.android.worldnewsapp.Backend.Database.DataAccessObject;

import com.example.android.worldnewsapp.Backend.Database.Model.NewsLocal;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public interface TheNewsDao {
    @Insert
    void insert(NewsLocal news);

    @Update
    void update(NewsLocal news);

    @Delete
    void delete(NewsLocal news);

    @Query("DELETE FROM the_news_table WHERE news_category LIKE :categoryInput")
    void clearAllGeneralArticle(String categoryInput);

    @Query("SELECT * FROM the_news_table WHERE news_category LIKE (:categoryInput)")
    LiveData<List<NewsLocal>> getCategoryNewsArticles(String categoryInput);

    @Query("SELECT * FROM the_news_table WHERE news_category LIKE (:categoryInput) LIMIT 5")
    LiveData<List<NewsLocal>> getTopTenCategoryNewsArticles(String categoryInput);


    @Insert
    void insertArticles(List<NewsLocal> articles);

    @Query("DELETE FROM news_table")
    void clearAllArticles();

    @Transaction
    default void clearAndCacheArticles(List<NewsLocal> articles) {
        clearAllArticles();
        insertArticles(articles);
    }

    /**
     * Get all the articles from table
     */
    @Query("SELECT * FROM the_news_table")
    LiveData<List<NewsLocal>> getNewsArticles();



    /*@Query("SELECT * FROM note_table WHERE nid IN (:noteIds)")
    LiveData<List<Note>> loadAllByIds(int[] noteIds);

    @Query("SELECT * FROM note_table WHERE note_title LIKE :title LIMIT 1")
    LiveData<List<Note>> findByTitle(String title);*/

}