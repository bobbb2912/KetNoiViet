package com.hien.ketnoiviet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hien.ketnoiviet.model.District;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.model.District;
import com.hien.ketnoiviet.model.Province;

import java.util.ArrayList;

public class DistrictAdapter extends BaseAdapter {
    ArrayList<District> arraylistdistrict;
    Context context;

    public DistrictAdapter(ArrayList<District> arraylistdistrict, Context context) {
        this.arraylistdistrict = arraylistdistrict;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylistdistrict.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylistdistrict.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView textview_district;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DistrictAdapter.ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new DistrictAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_huyen, null);
            viewHolder.textview_district = view.findViewById(R.id.textview_district);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (DistrictAdapter.ViewHolder) view.getTag();
        }
        District district = (District) getItem(i);
        viewHolder.textview_district.setText(district.getName());
        return view;
    }
}
