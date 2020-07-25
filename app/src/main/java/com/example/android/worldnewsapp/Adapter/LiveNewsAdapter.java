package com.example.android.worldnewsapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.worldnewsapp.Database.Model.NewsLocal;
import com.example.android.worldnewsapp.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class LiveNewsAdapter extends ListAdapter<NewsLocal, LiveNewsAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<NewsLocal> DIFF_CALLBACK = new DiffUtil.ItemCallback<NewsLocal>() {
        @Override
        public boolean areItemsTheSame(@NonNull NewsLocal oldItem, @NonNull NewsLocal newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull NewsLocal oldItem, @NonNull NewsLocal newItem) {
            /*return oldItem.getAuthor().equals(newItem.getAuthor()) &&
                    oldItem.getUrl().equals(newItem.getUrl()) &&
                    oldItem.getUrlToImage().equals(newItem.getUrlToImage());*/
            return oldItem.getUrl().equals(newItem.getUrl());
        }
    };

    private OnItemClickListener listener;
    private static CircleImageView newsImage;

    public LiveNewsAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsLocal currentNews = getItem(position);
        String aNewsTitle = currentNews.getTitle();
        String aNewsUrl = currentNews.getUrl();
        String aNewsAuthor = currentNews.getAuthor();
        String aNewsImage = currentNews.getUrlToImage();
        if (aNewsAuthor == null) {
            aNewsAuthor = "Anonymous";
        }
        holder.newsTitle.setText(aNewsTitle);
        holder.url.setText(aNewsUrl);
        holder.author.setText(aNewsAuthor);
        showImage(aNewsImage);
    }

    public NewsLocal getNewslocalAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(NewsLocal newsLocal);
    }

    private void showImage(String url) {
        if (url != null && url.isEmpty() == false) {
            Picasso.get().load(url).resize(200, 200).centerCrop().into(newsImage);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView newsTitle;
        TextView url;
        TextView author;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.title);
            url = itemView.findViewById(R.id.url);
            author = itemView.findViewById(R.id.author);
            newsImage = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }
    }
}
