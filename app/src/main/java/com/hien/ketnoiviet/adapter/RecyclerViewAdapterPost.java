package com.hien.ketnoiviet.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Others.PersonalOtherInfo;
import com.hien.ketnoiviet.Others.PostDetail;
import com.hien.ketnoiviet.Others.imgFitScreen;
import com.hien.ketnoiviet.Personal.PersonalInfo;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.model.Post;
import com.hien.ketnoiviet.ultil.Server;

import java.util.ArrayList;

public class RecyclerViewAdapterPost extends RecyclerView.Adapter<RecyclerViewAdapterPost.ViewHolder> {

    Context context;
    ArrayList<Post> arraypost;

    public RecyclerViewAdapterPost(Context context, ArrayList<Post> arraypost) {
        this.context = context;
        this.arraypost = arraypost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_listview_bai_viet,parent,false);
        return new RecyclerViewAdapterPost.ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        holder.user_name.setText(arraypost.get(position).getNameuser()+"");
        holder.timeandplace.setText(arraypost.get(position).getDatepost()+" tại " + arraypost.get(position).getProvince());
        holder.mo_ta.setText("Địa chỉ: " + arraypost.get(position).getAddress()+"");
        holder.ten_dia_diem.setText(arraypost.get(position).getNameplace()+"");
        holder.noi_dung.setText(arraypost.get(position).getContent()+"");
        String hinhanh1 = Server.imagesget + arraypost.get(position).getImage1();
        String hinhanhuser = Server.userget + arraypost.get(position).getImageuser();
        Glide.with(context).load(hinhanh1).into(holder.image1);
        Glide.with(context).load(hinhanhuser).into(holder.img_user_post);
        holder.img_user_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneuser = arraypost.get(position).getPhoneuser();
                if (phoneuser.equals(HomeActivity.phone_number_user)){
                    Intent intent = new Intent(context, PersonalInfo.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, PersonalOtherInfo.class);
                    intent.putExtra("phoneuser", arraypost.get(position).getPhoneuser());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
        holder.user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneuser = arraypost.get(position).getPhoneuser();
                if (phoneuser.equals(HomeActivity.phone_number_user)){
                    Intent intent = new Intent(context, PersonalInfo.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, PersonalOtherInfo.class);
                    intent.putExtra("phoneuser", arraypost.get(position).getPhoneuser());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
        holder.xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                                Toast.makeText(context, "" + post.getIdpost(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PostDetail.class);
                intent.putExtra("idpost", arraypost.get(position).getIdpost());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.noi_dung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                                Toast.makeText(context, "" + post.getIdpost(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PostDetail.class);
                intent.putExtra("idpost", arraypost.get(position).getIdpost());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, imgFitScreen.class);
                intent.putExtra("url1", hinhanh1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arraypost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout like_post,comment_post,share_post;
        public TextView user_name,timeandplace,ten_dia_diem,mo_ta,noi_dung,xemthem;
        public ImageView image1,img_user_post,icon_heart_no;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            timeandplace = itemView.findViewById(R.id.timeandplace);
            ten_dia_diem = itemView.findViewById(R.id.ten_dia_diem);
            mo_ta = itemView.findViewById(R.id.mo_ta);
            noi_dung = itemView.findViewById(R.id.noi_dung);
            image1 = itemView.findViewById(R.id.img1);
            img_user_post = itemView.findViewById(R.id.img_user_post);
            xemthem = itemView.findViewById(R.id.xem_them_post);
        }
    }
}
