package com.siddharth.searchrepogithub;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Siddharth on 21/6/19.
 */
public class Utils {

    private static Utils INSTANCE;

    public static Utils getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Utils();
        }

        return INSTANCE;
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
