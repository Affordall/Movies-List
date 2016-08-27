package com.testapps.movieslist.di.modules;

import android.app.Application;

import com.testapps.movieslist.app.MoviesListApp;
import com.testapps.movieslist.models.Movie;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = {
                DatabaseModule.class,
                NetworkModule.class
        }
)
public class MoviesListAppModule {

    private final MoviesListApp app;

    public MoviesListAppModule(MoviesListApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    ArrayList<Movie> providesMoviesList(){
        return new ArrayList<>();
    }
}
