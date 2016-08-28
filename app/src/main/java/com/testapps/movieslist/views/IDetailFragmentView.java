package com.testapps.movieslist.views;

import com.testapps.movieslist.models.Movie;

public interface IDetailFragmentView {
    void updateViews(Movie result);
    void showProgressDialog();
    void hideProgressDialog();
    Movie getMovie(long id);
}
