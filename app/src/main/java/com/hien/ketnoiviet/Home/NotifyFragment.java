package com.hien.ketnoiviet.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hien.ketnoiviet.Others.NewsDetail;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.NewsAdapter;
import com.hien.ketnoiviet.adapter.PostAdapter;
import com.hien.ketnoiviet.model.News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NotifyFragment extends Fragment {

    ListView listview_title;
    LinearLayout linearLayout_dangbaisc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notify, container, false);
        listview_title = view.findViewById(R.id.listview_tintuc);
        linearLayout_dangbaisc = view.findViewById(R.id.linearLayout_dangbaisc);
        linearLayout_dangbaisc.setVisibility(View.GONE);

        if (HomeActivity.isPost.equals(1)){
            linearLayout_dangbaisc.setVisibility(View.VISIBLE);
        }
//        listview_title.setAdapter(HomeActivity.adapter);
        listview_title.setAdapter(HomeActivity.newsAdapter);
//        listview_title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getContext(), NewsDetail.class);
//                intent.putExtra("linkNews", HomeActivity.arrayLink.get(i));
//                startActivity(intent);
//            }
//        });

        return view;

    }
}
