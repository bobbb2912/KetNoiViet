package com.hien.ketnoiviet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hien.ketnoiviet.Others.NewsDetail;
import com.hien.ketnoiviet.Others.imgFitScreen;
import com.hien.ketnoiviet.model.News;
import com.hien.ketnoiviet.Others.NewsDetail;
import com.hien.ketnoiviet.Others.imgFitScreen;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.model.News;
import com.hien.ketnoiviet.model.Post;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {

    Context context;
    ArrayList<News> arrayNew;

    public NewsAdapter(Context context, ArrayList<News> arrayNew) {
        this.context = context;
        this.arrayNew = arrayNew;
    }

    @Override
    public int getCount() {
        return arrayNew.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayNew.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView title_news,datePost_news,description_news;
        public ImageView image_news;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        NewsAdapter.ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new NewsAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_tintuc, null);
            viewHolder.title_news = view.findViewById(R.id.title_news);
            viewHolder.datePost_news = view.findViewById(R.id.datePost_news);
            viewHolder.image_news = view.findViewById(R.id.image_news);
            viewHolder.description_news = view.findViewById(R.id.description_news);
            view.setTag(viewHolder);
        } else {
            viewHolder = (NewsAdapter.ViewHolder) view.getTag();
        }
        News news = (News) getItem(i);
        viewHolder.title_news.setText(news.getTitlel() + "");
        String date = news.getDate();
        String[] parts = date.split(" ");
        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556
        //2021-11-25 02:38:15
        //part1 = 2021-11-25
        String[] part10 = part1.split("-");
        String part11 = part10[0]; // 004
        String part12 = part10[1];
        String part13 = part10[2];
        //part2 = 02:38:15
        String[] part20 = part2.split(":");
        String part21 = part20[0]; // 004
        String part22 = part20[1];
        String part23 = part20[2];
        viewHolder.datePost_news.setText("Vào lúc " + part21 + ":" + part22 + " ngày " + part13 + "/" + part12 + "/" + part11);
        viewHolder.description_news.setText(news.getDescription() + "");
        Glide.with(context).load(news.getThumbnail()).into(viewHolder.image_news);
        viewHolder.title_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetail.class);
                intent.putExtra("linkNews", news.getUrl());
                context.startActivity(intent);
            }
        });
        viewHolder.image_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, imgFitScreen.class);
                intent.putExtra("url1", news.getThumbnail());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.description_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetail.class);
                intent.putExtra("linkNews", news.getUrl());
                context.startActivity(intent);
            }
        });
        viewHolder.datePost_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetail.class);
                intent.putExtra("linkNews", news.getUrl());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
