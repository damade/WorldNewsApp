package com.example.android.worldnewsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.worldnewsapp.Model.News;
import com.example.android.worldnewsapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{
    private List<News> news;
    private int rowLayout;
    private Context context;
    private static CircleImageView newsImage;

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout newsLayout;
        TextView newsTitle;
        TextView url;
        TextView author;



        public NewsViewHolder(View v) {
            super(v);
            newsLayout = v.findViewById(R.id.news_layout);
            newsTitle =  v.findViewById(R.id.title);
            url =  v.findViewById(R.id.url);
            author =  v.findViewById(R.id.author);
            newsImage =  v.findViewById(R.id.image);
        }
    }

    public NewsAdapter(List<News> news, int rowLayout, Context context) {
        this.news = news;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new NewsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
        holder.newsTitle.setText(news.get(position).getTitle());
        holder.url.setText(news.get(position).getUrl());
        holder.author.setText(news.get(position).getAuthor());
        showImage(news.get(position).getUrlToImage());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    private void showImage(String url) {
        if (url != null && url.isEmpty() == false) {
            Picasso.get().load(url).resize(200,200).centerCrop().into(newsImage);
        }

    }
}
