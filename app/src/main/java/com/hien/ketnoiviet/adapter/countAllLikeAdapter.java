package com.hien.ketnoiviet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hien.ketnoiviet.model.Like;
import com.hien.ketnoiviet.model.Like;

import java.util.ArrayList;

public class countAllLikeAdapter extends BaseAdapter {
    Context context;
    ArrayList<Like> arrayLike;
    public countAllLikeAdapter(Context context, ArrayList<Like> arrayLike) {
        this.context = context;
        this.arrayLike = arrayLike;
    }

    @Override
    public int getCount() {
        return arrayLike.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }


}
