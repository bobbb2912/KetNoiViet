package com.hien.ketnoiviet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.model.District;
import com.hien.ketnoiviet.model.Ward;

import java.util.ArrayList;

public class WardAdapter extends BaseAdapter {
    ArrayList<Ward> arraylistward;
    Context context;

    public WardAdapter(ArrayList<Ward> arraylistward, Context context) {
        this.arraylistward = arraylistward;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylistward.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylistward.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView textview_ward;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        WardAdapter.ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new WardAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_phuong, null);
            viewHolder.textview_ward = view.findViewById(R.id.textview_ward);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (WardAdapter.ViewHolder) view.getTag();
        }
        Ward ward = (Ward) getItem(i);
        viewHolder.textview_ward.setText(ward.getName());
        return view;
    }
}
