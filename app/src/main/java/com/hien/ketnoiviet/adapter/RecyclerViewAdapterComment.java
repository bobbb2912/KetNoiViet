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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Others.PersonalOtherInfo;
import com.hien.ketnoiviet.Others.PostDetail;
import com.hien.ketnoiviet.Personal.PersonalInfo;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.model.Comment;
import com.hien.ketnoiviet.model.Post;
import com.hien.ketnoiviet.ultil.Server;

import java.util.ArrayList;

public class RecyclerViewAdapterComment extends RecyclerView.Adapter<RecyclerViewAdapterComment.ViewHolder>{

    Context context;
    ArrayList<Comment> arrayComment ;

    public RecyclerViewAdapterComment(Context context, ArrayList<Comment> arrayComment) {
        this.context = context;
        this.arrayComment = arrayComment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_listview_binhluan,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.user_name.setText(arrayComment.get(position).getNameUser()+"");
        String hinhanhuser = Server.userget + arrayComment.get(position).getImageUser();
        holder.content.setText(arrayComment.get(position).getContent()+"");
        Glide.with(context).load(hinhanhuser).into(holder.img_user_comment);

        holder.img_user_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneuser = arrayComment.get(position).getPhoneUser();
                if (phoneuser.equals(HomeActivity.phone_number_user)){
                    Intent intent = new Intent(context, PersonalInfo.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, PersonalOtherInfo.class);
                    intent.putExtra("phoneuser", arrayComment.get(position).getPhoneUser());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        holder.user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneuser = arrayComment.get(position).getPhoneUser();
                if (phoneuser.equals(HomeActivity.phone_number_user)){
                    Intent intent = new Intent(context, PersonalInfo.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, PersonalOtherInfo.class);
                    intent.putExtra("phoneuser", arrayComment.get(position).getPhoneUser());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView user_name,content;
        public ImageView img_user_comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.name_user_comment);
            content = itemView.findViewById(R.id.content_user_comment);
            img_user_comment = itemView.findViewById(R.id.img_user_comment);
        }
    }
}
