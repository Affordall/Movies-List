package com.testapps.movieslist.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.testapps.movieslist.R;
import com.testapps.movieslist.app.MoviesListApp;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

public class Utils {

    private static int deviceAPI = Build.VERSION.SDK_INT;
    private static final Handler mHandler = new Handler();

    private static Context mContext = MoviesListApp.getContext();

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void shareIt(String url) {
        mHandler.post(() -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, url);
            Intent intent = Intent.createChooser(sharingIntent, mContext.getString(R.string.share_via));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });
    }

    @Contract(pure = true)
    public static boolean isKitkat() {
        return deviceAPI >= Build.VERSION_CODES.KITKAT;
    }

    @Contract(pure = true)
    public static boolean isLollipop() {
        return deviceAPI >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Contract(pure = true)
    public static boolean isMarshmallow() {
        return deviceAPI >= Build.VERSION_CODES.M;
    }

    @Contract(pure = true)
    public static boolean isNougat() {
        return deviceAPI >= Build.VERSION_CODES.N;
    }

    public static void logError(Exception e) {
        Log.e("-CHP-ERROR:", " " + e.getMessage());
    }

}
