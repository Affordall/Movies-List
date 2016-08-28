package com.testapps.movieslist.views;

import android.content.Context;

import com.testapps.movieslist.models.Movie;

import java.util.ArrayList;

public interface IListFragmentView {
    void setMoviesListAdapter(ArrayList<Movie> itemMoviesList);
    void addListToAdapter(ArrayList<Movie> itemMoviesList);
    void getMoviesItems();
    void showProgressDialog();
    void hideProgressDialog();
    void replaceToDetailFragment(long id);
}
