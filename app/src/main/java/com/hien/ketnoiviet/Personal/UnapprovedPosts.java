package com.hien.ketnoiviet.Personal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.PostAdapter;
import com.hien.ketnoiviet.adapter.RecyclerViewAdapterPost;
import com.hien.ketnoiviet.model.Post;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UnapprovedPosts extends AppCompatActivity {

    public ArrayList<Post> arrayUnApproved_post_personal;
    public RecyclerViewAdapterPost postAdapterInfo;
    Toolbar toolbar_unapproved_post;
    String phonenumber_user = "";
    LinearLayout chua_co_bai_viet_unapost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unapproved_posts);
        viewbinding();
        event();
        getPost();

        phonenumber_user = HomeActivity.phone_number_user;
        arrayUnApproved_post_personal = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL
                , false);
        RecyclerView recyclerView = findViewById(R.id.list_unapproved_post);
        recyclerView.setLayoutManager(layoutManager);
        postAdapterInfo = new RecyclerViewAdapterPost(this, arrayUnApproved_post_personal);
        recyclerView.setAdapter(postAdapterInfo);
    }

    private void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter==null) {
            return;
        }

        int totalHeight=0;
        for (int size=0; size < myListAdapter.getCount(); size++) {
            View listItem=myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight+=listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params= myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
    }

    private void getPost() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getUnApprovedPost, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
//                Log.d("response", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        if (jsonArray.length()>0){
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int idpost = jsonObject.getInt("id");
                                String nameplace = jsonObject.getString("nameplace");
                                String province  =  jsonObject.getString("province");
                                String district  =  jsonObject.getString("district");
                                String ward  =  jsonObject.getString("ward");
                                String address  =  jsonObject.getString("address");
                                String description = jsonObject.getString("description");
                                String content = jsonObject.getString("content");
                                String image1 = jsonObject.getString("image1");
                                String image2 = jsonObject.getString("image2");
                                String image3 = jsonObject.getString("image3");
                                String image4 = jsonObject.getString("image4");
                                String phoneuser = jsonObject.getString("phoneuser");
                                String datepost = jsonObject.getString("datepost");
                                int status = jsonObject.getInt("status");
                                String nameuser = jsonObject.getString("nameuser");
                                String imageuser = jsonObject.getString("imageuser");

//                              Toast.makeText(getContext(), "Bài viết:" + idpost + "\n" + nameplace + "\n" + address + "\n" + image1 + "\n" + phoneuser + "\n" + datepost + "\n" + status + "\n", Toast.LENGTH_SHORT).show();
                                arrayUnApproved_post_personal.add(new Post(idpost, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status,nameuser,imageuser));
                                postAdapterInfo.notifyDataSetChanged();
                        }
                        }else {
                            chua_co_bai_viet_unapost.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phoneuser", HomeActivity.phone_number_user);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void event() {
        toolbar_unapproved_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnapprovedPosts.this.finish();
            }
        });
    }

    private void viewbinding() {
        toolbar_unapproved_post = findViewById(R.id.toolbar_unapproved_post);
        chua_co_bai_viet_unapost = findViewById(R.id.chua_co_bai_viet_unapost);
        chua_co_bai_viet_unapost.setVisibility(View.GONE);
    }

    //region check internet
    com.hien.ketnoiviet.ultil.networkChangeListener networkChangeListener = new networkChangeListener();
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    //endregion
}