package com.hien.ketnoiviet.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Others.PersonalOtherInfo;
import com.hien.ketnoiviet.Others.PostDetail;
import com.hien.ketnoiviet.Others.imgFitScreen;
import com.hien.ketnoiviet.Personal.PersonalInfo;
import com.hien.ketnoiviet.model.Post;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Others.PersonalOtherInfo;
import com.hien.ketnoiviet.Others.PostDetail;
import com.hien.ketnoiviet.Others.imgFitScreen;
import com.hien.ketnoiviet.Personal.PersonalInfo;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.model.Post;
import com.hien.ketnoiviet.ultil.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostAdapter extends BaseAdapter {

    Context context;
    ArrayList<Post> arraypost;

    public PostAdapter(Context context, ArrayList<Post> arraypost) {
        this.context = context;
        this.arraypost = arraypost;
    }

    @Override
    public int getCount() {
        return arraypost.size();
    }

    @Override
    public Object getItem(int i) {
        return arraypost.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public LinearLayout like_post,comment_post,share_post;
        public TextView user_name,timeandplace,ten_dia_diem,mo_ta,noi_dung,xemthem;
        public ImageView image1,img_user_post,icon_heart_no, bookmark_post_details;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_bai_viet, null);
            viewHolder.user_name = view.findViewById(R.id.user_name);
            viewHolder.timeandplace = view.findViewById(R.id.timeandplace);
            viewHolder.ten_dia_diem = view.findViewById(R.id.ten_dia_diem);
            viewHolder.mo_ta = view.findViewById(R.id.mo_ta);
            viewHolder.noi_dung = view.findViewById(R.id.noi_dung);
            viewHolder.image1 = view.findViewById(R.id.img1);
            viewHolder.img_user_post = view.findViewById(R.id.img_user_post);
            viewHolder.xemthem = view.findViewById(R.id.xem_them_post);
            viewHolder.bookmark_post_details = view.findViewById(R.id.bookmark_post_details);
//            viewHolder.like_post = view.findViewById(R.id.like_post);
//            viewHolder.comment_post = view.findViewById(R.id.comment_post);
//            viewHolder.share_post = view.findViewById(R.id.share_post);
//            viewHolder.icon_heart_no = view.findViewById(R.id.icon_heart_no);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Post post = (Post) getItem(i);
        viewHolder.user_name.setText(post.getNameuser()+"");
        viewHolder.timeandplace.setText(" • Lúc " + post.getDatepost() + " • Tại " + post.getProvince());
        viewHolder.mo_ta.setText("Địa chỉ: " + post.getAddress()+"");
        viewHolder.ten_dia_diem.setText(post.getNameplace()+"");
        viewHolder.noi_dung.setText(post.getContent()+"");
        String hinhanh1 = Server.imagesget + post.getImage1();
        String hinhanhuser = Server.userget + post.getImageuser();
        Glide.with(context).load(hinhanh1).into(viewHolder.image1);
        Glide.with(context).load(hinhanhuser).into(viewHolder.img_user_post);
        viewHolder.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, imgFitScreen.class);
                intent.putExtra("url1", hinhanh1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.img_user_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneuser = post.getPhoneuser();
                if (phoneuser.equals(HomeActivity.phone_number_user)){
                    Intent intent = new Intent(context, PersonalInfo.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, PersonalOtherInfo.class);
                    intent.putExtra("phoneuser", post.getPhoneuser());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
        viewHolder.user_name.setOnClickListener(view14 -> {
            String phoneuser = post.getPhoneuser();
            if (phoneuser.equals(HomeActivity.phone_number_user)){
                Intent intent = new Intent(context, PersonalInfo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
            else {
                Intent intent = new Intent(context, PersonalOtherInfo.class);
                intent.putExtra("phoneuser", post.getPhoneuser());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        viewHolder.xemthem.setOnClickListener(view13 -> {
//                                Toast.makeText(context, "" + post.getIdpost(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, PostDetail.class);
            intent.putExtra("idpost", post.getIdpost());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        viewHolder.noi_dung.setOnClickListener(view12 -> {
//                                Toast.makeText(context, "" + post.getIdpost(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, PostDetail.class);
            intent.putExtra("idpost", post.getIdpost());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        viewHolder.bookmark_post_details.setOnClickListener(view1 -> Toast.makeText(context  ,"Chức năng lưu địa điểm chưa cập nhật!", Toast.LENGTH_SHORT).show());
        return view;
    }

    private void addheart(String idpost, String iduser) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, Server.addreact
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(context, response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Lỗi" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idpost", idpost);
                params.put("phoneuser", iduser);
                return params;
            }
        };
        requestQueue.add(request);
    }

}
