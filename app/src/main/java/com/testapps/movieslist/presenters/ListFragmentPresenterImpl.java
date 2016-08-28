package com.testapps.movieslist.presenters;

import android.os.AsyncTask;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.testapps.movieslist.app.MoviesListApp;
import com.testapps.movieslist.models.Movie;
import com.testapps.movieslist.network.parser.JsonParser;
import com.testapps.movieslist.utils.Utils;
import com.testapps.movieslist.views.IListFragmentView;

import java.util.ArrayList;

import javax.inject.Inject;

public class ListFragmentPresenterImpl implements IListFragmentPresenter {

    private IListFragmentView view;

    @Inject
    public ListFragmentPresenterImpl() {
    }

    @Override
    public void init(IListFragmentView view) {
        this.view=view;
    }

    @Override
    public void onResume() {
        parseThemAll();
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onItemClick(Movie itemMovie) {
        view.replaceToDetailFragment(itemMovie.getId());
    }

    @Override
    public void addListToAdapter(ArrayList<Movie> movies) {
        view.addListToAdapter(movies);
    }

    @Override
    public void onParse() {
        parseThemAll();
    }

    public class RefreshServiceAT extends AsyncTask<Void, Void,  ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... arg) {
            return JsonParser.getInstance(MoviesListApp.getContext()).getMoviesList();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> result) {
            super.onPostExecute(result);
            view.setMoviesListAdapter(result);
            view.hideProgressDialog();
        }
    }

    private void parseThemAll() {
        if (!Utils.isNetworkAvailable()) {
            view.hideProgressDialog();
        } else {
            new RefreshServiceAT().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }


}
