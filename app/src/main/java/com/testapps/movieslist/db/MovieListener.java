package com.testapps.movieslist.db;

import com.testapps.movieslist.models.Movie;

import java.util.ArrayList;

public interface MovieListener {
    void addMovieItem(Movie movieItem);
    ArrayList<Movie> getAllMovies();
    int getMoviesItemCount();
}
