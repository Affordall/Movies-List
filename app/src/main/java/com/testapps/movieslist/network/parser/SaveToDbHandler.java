package com.testapps.movieslist.network.parser;

import android.content.Context;

import com.testapps.movieslist.app.MoviesListApp;
import com.testapps.movieslist.db.DatabaseHandler;
import com.testapps.movieslist.models.Movie;

import javax.inject.Inject;

/**
 * Created by affy on 27.08.16.
 */
public class SaveToDbHandler {

    private Context mContext;

    @Inject DatabaseHandler handler;

    @Inject
    public SaveToDbHandler(Context context) {
        this.mContext = context;
        MoviesListApp.get(mContext).getAppComponent().inject(this);
    }

    public void saveMovies(Movie item) {
        if (!handler.isMovieExist(item.getId())) {
            handler.addMovieItem(item);
        }
    }
}
