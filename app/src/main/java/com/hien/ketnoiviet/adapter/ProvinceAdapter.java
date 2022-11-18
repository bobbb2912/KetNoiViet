package com.hien.ketnoiviet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.model.Province;

import java.util.ArrayList;

public class ProvinceAdapter extends BaseAdapter {

    ArrayList<Province> arraylistprovince;
    Context context;

    public ProvinceAdapter(ArrayList<Province> arraylistprovince, Context context) {
        this.arraylistprovince = arraylistprovince;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylistprovince.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylistprovince.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView textview_province;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_tinh, null);
            viewHolder.textview_province = view.findViewById(R.id.textview_province);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        Province province = (Province) getItem(i);
        viewHolder.textview_province.setText(province.getName());
        return view;
    }
}
