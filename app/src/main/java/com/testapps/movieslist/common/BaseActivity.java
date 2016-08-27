package com.testapps.movieslist.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.testapps.movieslist.app.MoviesListApp;
import com.testapps.movieslist.di.components.MoviesListAppComponent;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(MoviesListApp.get(this).getAppComponent());
    }

    protected abstract void setupComponent(MoviesListAppComponent appComponent);

}
