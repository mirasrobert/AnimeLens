package com.example.imotaku.utility;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public abstract class Common {

    // Check Internet
    public static boolean isConnectedToInternet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {

            /* Returns connection status information about
             * all network types supported by the device
             */

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {

                // Check Connection State If Connected

                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

}
