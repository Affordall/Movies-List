package com.testapps.movieslist.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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

    public static void shareIt(String title) {
        mHandler.post(() -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, title);
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

    public static boolean isTwoPane(Context context) {
        return ((AppCompatActivity)context).findViewById(R.id.frag_single_container) != null;
    }

    public static void setFragment(Context context, Fragment fragment, boolean addToBackStack, @Nullable String tag) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        try {
            if(!fragment.isAdded()) {

                fragmentTransaction.replace(R.id.frag_container, fragment, tag);
                if (addToBackStack) {
                    fragmentTransaction.addToBackStack("");
                }
                fragmentTransaction.commit();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

}
