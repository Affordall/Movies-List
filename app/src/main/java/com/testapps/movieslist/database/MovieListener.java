package com.testapps.movieslist.database;

import com.testapps.movieslist.models.Movie;

import java.util.ArrayList;

public interface MovieListener {
    void addMovieItem(Movie movieItem);
    ArrayList<Movie> getAllMovies();
    int getMoviesItemCount();
}
