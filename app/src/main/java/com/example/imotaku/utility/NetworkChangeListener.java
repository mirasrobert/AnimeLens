package com.example.imotaku.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.helper.widget.Layer;

import com.example.imotaku.R;

// Use Broadcast Receiver
public class NetworkChangeListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Check if NOT connected to internet
        if (!Common.isConnectedToInternet(context)) {
            // Show Dialog No Internet
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            // Inflate Dialog
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_dialog, null);
            builder.setView(layout_dialog);

            AppCompatButton restartBtn = layout_dialog.findViewById(R.id.restartBtn);

            // Show Dialog
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);

            dialog.getWindow().setGravity(Gravity.CENTER);

            // If Button is clicked
            restartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Dismiss / Hide Dialog
                    dialog.dismiss();
                    onReceive(context, intent);

                    ((Activity) context).finish();

                }
            });


        }
    }
}
