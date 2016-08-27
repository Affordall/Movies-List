package com.testapps.movieslist.app;

import android.app.Application;
import android.content.Context;

import com.testapps.movieslist.BuildConfig;
import com.testapps.movieslist.di.components.DaggerMoviesListAppComponent;
import com.testapps.movieslist.di.components.MoviesListAppComponent;
import com.testapps.movieslist.di.modules.MoviesListAppModule;

import timber.log.Timber;


public class MoviesListApp extends Application {

    private MoviesListAppComponent appComponent;
    private static Application instance;

    public static MoviesListApp get(Context context) {
        return (MoviesListApp) context.getApplicationContext();
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        buildGraphAndInject();
    }

    public MoviesListAppComponent getAppComponent() {
        return appComponent;
    }

    public void buildGraphAndInject() {
        appComponent = DaggerMoviesListAppComponent.builder()
                .moviesListAppModule(new MoviesListAppModule(this))
                .build();
        appComponent.inject(this);
    }

}
