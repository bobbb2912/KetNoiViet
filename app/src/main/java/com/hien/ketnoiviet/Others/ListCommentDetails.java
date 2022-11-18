package com.hien.ketnoiviet.Others;

import static com.hien.ketnoiviet.ultil.Server.getTotalLike;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Intro.IntroOneActivity;
import com.hien.ketnoiviet.Intro.IntroTwoActivity;
import com.hien.ketnoiviet.Intro.MainActivity;
import com.hien.ketnoiviet.Login.ResetPhoneActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.CommentAdapter;
import com.hien.ketnoiviet.model.Comment;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListCommentDetails extends AppCompatActivity{

    //Vuốt trái, phải màn hình
    GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;
    //

    int totalLike = 0;
    int id = 0;
    int status = 0;
    ImageButton send_content_comment_post_ds;
    EditText comment_post_detail_edt_ds;
    ImageButton icon_like_inds_comment_details;
    TextView solike_in_dscmt_details;
    ListView list_comments_details_act;
    public ArrayList<Comment> arrayCommentDetails;
    public CommentAdapter commentAdapterDetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comment_details);
        AnhXa();

        gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };
        list_comments_details_act.setOnTouchListener(gestureListener);

        send_content_comment_post_ds = findViewById(R.id.send_content_comment_post_ds);
        comment_post_detail_edt_ds = findViewById(R.id.comment_post_detail_edt_ds);
        GetDataComment();
        send_content_comment_post_ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = comment_post_detail_edt_ds.getText().toString();
                comment_post_detail_edt_ds.clearFocus();
                if (content.equals("")){
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng nhập nội dung!", "Đóng");
                }
                else {
                    sound.playSound(ListCommentDetails.this, R.raw.post_like_cmt);
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.POST, Server.addcommnent
                            , new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            comment_post_detail_edt_ds.setText("");
                            GetDataComment();
                        }
                    }, error -> {
                        Show_SnackBar(R.drawable.icon_toast_warning, "Lỗi!", "Đóng");
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            params.put("idpost", String.valueOf(id));
                            params.put("phoneuser", HomeActivity.phone_number_user);
                            params.put("content", content + "");
                            return params;
                        }
                    };
                    requestQueue.add(request);
                }
            }
        });
        getTotalLike();
        getStatus();
        icon_like_inds_comment_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound.playSound(ListCommentDetails.this, R.raw.post_like_cmt);
                addheart();
                arrayCommentDetails.clear();
                ListCommentDetails.this.finish();
                startActivity(getIntent());
            }
        });

    }

    private void getStatus() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Server.getStatusReact
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                status = Integer.parseInt(response);
                checkstt();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Lỗi" + error.getMessage(), Toast.LENGTH_LONG).show();
                Show_SnackBar(R.drawable.icon_toast_warning, "Lỗi!", "Đóng");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idpost", String.valueOf(id));
                params.put("phoneuser", HomeActivity.phone_number_user);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void checkstt() {
        if (status == 1){
            icon_like_inds_comment_details.setImageResource(R.drawable.iicon_heart_white);
        }
        if (status == 0){
            icon_like_inds_comment_details.setImageResource(R.drawable.icon_favorite_border_white);
        }
    }

    private void addheart() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Server.addreact
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show(); v them nha cai react model a, thử đi, cơ mà biết đang làm gì không ấy :v
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Lỗi" + error.getMessage(), Toast.LENGTH_LONG).show();
                Show_SnackBar(R.drawable.icon_toast_warning, "Lỗi!", "Đóng");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idpost", String.valueOf(id));
                params.put("phoneuser", HomeActivity.phone_number_user);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void getTotalLike() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getTotalLike, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    totalLike = jsonArray.length();//ok chua r
                    solike_in_dscmt_details.setText(totalLike + " thích");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu...");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idpost",String.valueOf(id));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        Intent intent = this.getIntent();
        id = intent.getIntExtra("idpost",-1);
        list_comments_details_act = findViewById(R.id.list_comments_details_act);
        arrayCommentDetails = new ArrayList<>();
        commentAdapterDetails = new CommentAdapter(arrayCommentDetails, getApplicationContext());
        list_comments_details_act.setAdapter(commentAdapterDetails);
        icon_like_inds_comment_details = findViewById(R.id.icon_like_inds_comment_details);
        solike_in_dscmt_details = findViewById(R.id.solike_in_dscmt_details);
    }



    private void GetDataComment() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getComment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayCommentDetails.clear();
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                if (!response.isEmpty()){
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String nameuser = jsonObject.getString("nameuser");
                            String phonenumber  =  jsonObject.getString("phonenumber");
                            String imageuser  =  jsonObject.getString("imageuser");
                            String content  =  jsonObject.getString("content");
                            arrayCommentDetails.add(new Comment(nameuser, phonenumber, imageuser, content));
                            commentAdapterDetails.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                param.put("idpost",String.valueOf(id));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    // Command for Slide Right to Left (<-----)
    public int YourSlideRightToLeft(int position) {
        Toast.makeText(
                ListCommentDetails.this,
                "<-- Your Slide Right to Left",
                Toast.LENGTH_SHORT).show();
        return position;
    }

    // Command for Slide Left to Right (<-----)
    public int YourSlideLeftToRight(int position) {
        Toast.makeText(
                ListCommentDetails.this,
                "---> Your Slide Left to Right",
                Toast.LENGTH_SHORT).show();
        return position;
    }

    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(
                            ListCommentDetails.this,
                            "<-- Your Slide Right to Left",
                            Toast.LENGTH_SHORT).show();


                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    // left to right swipe
                    ListCommentDetails.this.finish();
                }
            } catch (Exception e) {
                return true;
            }
            return true;
        }
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }
        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }
        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }
        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }
    }

    public  final void Show_SnackBar( int i, String t, String a){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_snackbar,findViewById(R.id.snack_constraint), false);

        ImageView icon = layout.findViewById(R.id.im_icon);
        icon.setImageResource(i);
        TextView text = layout.findViewById(R.id.tv_message);
        text.setText(t.toString());
        TextView action = layout.findViewById(R.id.tv_action);
        action.setText(a.toString());

        View view = findViewById(R.id.layout_list_comment_detail);
        int duration = 5000;
        String mess = "";
        Snackbar s = Snackbar.make(view, mess, duration);

        s.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout sl = (Snackbar.SnackbarLayout) s.getView();
        sl.setPadding(0,0,0,0);

        //for dismiss
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.dismiss();
            }
        });
        sl.addView(layout,0);
        s.show();
        sound.playSound(ListCommentDetails.this, R.raw.toast);
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