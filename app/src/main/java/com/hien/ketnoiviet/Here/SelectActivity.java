package com.hien.ketnoiviet.Here;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hien.ketnoiviet.R;

import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity {

    String data = "";
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ListView listview_select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Intent intent = this.getIntent();
        data = intent.getStringExtra("data");
        listview_select = findViewById(R.id.listview_select);
        if (data.equals("sapxep")){
            setDanhSachSX();
        }else if(data.equals("bankinh")){
            setDanhSachBK();
        }else {
            setDanhSachTL();
        }
        findViewById(R.id.close_select).setOnClickListener(v -> onBackPressed());

    }

    private void setDanhSachSX() {
        list = new ArrayList<>();
        list.add("Bài viết mới nhất");
        list.add("Bài viết cũ nhất");
        list.add("Tất cả bài viết");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listview_select.setAdapter(adapter);
    }

    private void setDanhSachBK() {
        list = new ArrayList<>();
        list.add("Bán kính 10km");
        list.add("Bán kính 20km");
        list.add("Bán kính 30km");
        list.add("Bán kính 50km");
        list.add("Bán kính 70km");
        list.add("Bán kính 100km");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listview_select.setAdapter(adapter);
    }

    private void setDanhSachTL() {
        list = new ArrayList<>();
        list.add("Du lịch nghỉ dưỡng");
        list.add("Du lịch sinh thái");
        list.add("Du lịch văn hóa, lịch sử");
        list.add("Du lịch tham quan, khám phá");
        list.add("Du lịch teambuilding");
        list.add("Du lịch thể thao");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listview_select.setAdapter(adapter);
    }
}