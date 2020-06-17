package com.example.android.worldnewsapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.worldnewsapp.Model.LiveNews;
import com.example.android.worldnewsapp.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class LiveNewsAdapter extends ListAdapter<LiveNews, LiveNewsAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<LiveNews> DIFF_CALLBACK = new DiffUtil.ItemCallback<LiveNews>() {
        @Override
        public boolean areItemsTheSame(@NonNull LiveNews oldItem, @NonNull LiveNews newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull LiveNews oldItem, @NonNull LiveNews newItem) {
            return oldItem.getAuthor().equals(newItem.getAuthor()) &&
                    oldItem.getUrl().equals(newItem.getUrl()) &&
                    oldItem.getUrlToImage().equals(newItem.getUrlToImage());
        }
    };
    private static CircleImageView newsImage;
    //private OnItemClickListener listener;

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
        LiveNews currentNews = getItem(position);
        String aNewsTitle = currentNews.getTitle();
        String aNewsUrl = currentNews.getUrl();
        String aNewsAuthor = currentNews.getAuthor();
        String aNewsImage = currentNews.getUrlToImage();

        holder.newsTitle.setText(aNewsTitle);
        holder.url.setText(aNewsUrl);
        holder.author.setText(aNewsAuthor);
        showImage(aNewsImage);
    }

    public LiveNews getNoteAt(int position) {
        return getItem(position);
    }

    /*public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }*/

    private void showImage(String url) {
        if (url != null && url.isEmpty() == false) {
            Picasso.get().load(url).resize(200, 200).centerCrop().into(newsImage);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout newsLayout;
        TextView newsTitle;
        TextView url;
        TextView author;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsLayout = itemView.findViewById(R.id.news_layout);
            newsTitle = itemView.findViewById(R.id.title);
            url = itemView.findViewById(R.id.url);
            author = itemView.findViewById(R.id.author);
            newsImage = itemView.findViewById(R.id.image);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });*/

        }
    }
}
