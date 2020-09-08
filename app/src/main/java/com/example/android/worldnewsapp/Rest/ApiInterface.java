package com.example.android.worldnewsapp.Rest;

import com.example.android.worldnewsapp.Model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    /*@GET("movie/top_rated")
    Call<NewsResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<NewsResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/

    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);

    /*@GET("top-headlines")
    Call<LiveNewsResponse> getTopHeadlines(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);*/


}
