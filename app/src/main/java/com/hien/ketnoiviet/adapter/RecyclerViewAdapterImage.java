package com.hien.ketnoiviet.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hien.ketnoiviet.Discover.InfoByProvince;
import com.hien.ketnoiviet.Others.imgFitScreen;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.Server;

import java.util.ArrayList;

public class RecyclerViewAdapterImage extends RecyclerView.Adapter<RecyclerViewAdapterImage.ViewHolder>{

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterImage(ArrayList<String> mImageUrls, Context mContext) {
        this.mImageUrls = mImageUrls;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_listview_ngang_hinhanh,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(mContext).asBitmap().load(mImageUrls.get(position)).into(holder.image_bai_viet);
        holder.image_bai_viet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext.getApplicationContext(), "" + mImageUrls.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, imgFitScreen.class);
                intent.putExtra("url1",  mImageUrls.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_bai_viet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_bai_viet = itemView.findViewById(R.id.image_bai_viet);
        }
    }
}
