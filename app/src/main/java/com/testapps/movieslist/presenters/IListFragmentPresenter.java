package com.testapps.movieslist.presenters;

import com.testapps.movieslist.common.BaseFragmentPresenter;
import com.testapps.movieslist.models.Movie;
import com.testapps.movieslist.views.IListFragmentView;

import java.util.ArrayList;


public interface IListFragmentPresenter extends BaseFragmentPresenter<IListFragmentView> {
    void onResume();
    void onPause();
    void onParse();
    void onItemClick(Movie itemMovie);
    void addListToAdapter(ArrayList<Movie> movies);
}
