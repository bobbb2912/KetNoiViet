package com.hien.ketnoiviet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Others.PersonalOtherInfo;
import com.hien.ketnoiviet.Personal.PersonalInfo;
import com.hien.ketnoiviet.model.Comment;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Others.PersonalOtherInfo;
import com.hien.ketnoiviet.Personal.PersonalInfo;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.model.Comment;
import com.hien.ketnoiviet.ultil.Server;


import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    ArrayList<Comment> arraylistcomment;
    Context context;

    public CommentAdapter(ArrayList<Comment> arraylistcomment, Context context) {
        this.arraylistcomment = arraylistcomment;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylistcomment.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylistcomment.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        ImageView img_user_comment;
        TextView name_user_comment,content_user_comment;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_binhluan, null);
            viewHolder.img_user_comment = view.findViewById(R.id.img_user_comment);
            viewHolder.name_user_comment = view.findViewById(R.id.name_user_comment);
            viewHolder.content_user_comment = view.findViewById(R.id.content_user_comment);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (CommentAdapter.ViewHolder) view.getTag();
        }
        Comment comment = (Comment) getItem(i);
        String hinhanhuser = Server.userget + comment.getImageUser() +"";
        Glide.with(context).load(hinhanhuser).into(viewHolder.img_user_comment);
        viewHolder.name_user_comment.setText(comment.getNameUser()+"");
        viewHolder.content_user_comment.setText(comment.getContent()+"");
        viewHolder.name_user_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context.getApplicationContext(), "Tên", Toast.LENGTH_SHORT).show();
                String phoneuser = comment.getPhoneUser();
                if (phoneuser.equals(HomeActivity.phone_number_user)){
                    Toast.makeText(context.getApplicationContext(), "Giong", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, PersonalInfo.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
//                    Toast.makeText(context.getApplicationContext(), "Khac", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, PersonalOtherInfo.class);
                    intent.putExtra("phoneuser", comment.getPhoneUser());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
        viewHolder.img_user_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneuser = comment.getPhoneUser();
                if (phoneuser.equals(HomeActivity.phone_number_user)){
//                    Toast.makeText(context.getApplicationContext(), "Giong", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, PersonalInfo.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
//                    Toast.makeText(context.getApplicationContext(), "Khac", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, PersonalOtherInfo.class);
                    intent.putExtra("phoneuser", comment.getPhoneUser());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
        return view;
    }
}
