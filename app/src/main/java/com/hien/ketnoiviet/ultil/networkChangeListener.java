package com.hien.ketnoiviet.ultil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

import com.hien.ketnoiviet.R;

public class networkChangeListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!CheckConnection.haveNetworkConnection(context)) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, null);
//            builder.setView(layout_dialog);

            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_dialog_layout);

            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            AppCompatButton retry = dialog.findViewById(R.id.btn_okay);
            AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel);

            dialog.show();
            sound.playSound(context, R.raw.thongbao);
            dialog.setCancelable(false);

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            onReceive(context, intent);
                        }
                    }, 1000);

                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.exit(0);
                }
            });
        }
    }
}
