package com.example.android.worldnewsapp.Backend.Database.Model;

public class DatabaseDetails {

    //Source Model
    public final static String sourceParentColumns = "sourceId";
    public final static String sourceChildColumns = "id";
    public final static String sourceColumnId = "id";
    public final static String sourceColumnName = "source_name";

    //NewsLocal Model
    public final static String tableName = "the_news_table";
    public final static String newsColumnSource = "news_sources";
    public final static String newsColumnAuthor = "news_author";
    public final static String newsColumnTitle = "news_title";
    public final static String newsColumnDescription = "news_description";
    public final static String newsColumnUrl = "news_url";
    public final static String newsColumnUrlToImage = "news_url_to_image";
    public final static String newsColumnPublished = "news_published_at";
    public final static String newsColumnContent = "news_content";
    public final static String newsColumnCategory = "news_category";

    //Database
    public final static String databaseName = "the_news_database";

    //API key
    public final static String API_KEY = "7bf5a5d4b62e4d1c939e97ebf66167c4";

}
