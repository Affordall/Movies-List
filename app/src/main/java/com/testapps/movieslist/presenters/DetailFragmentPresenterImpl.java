package com.testapps.movieslist.presenters;


import android.os.AsyncTask;

import com.testapps.movieslist.models.Movie;
import com.testapps.movieslist.views.IDetailFragmentView;

import javax.inject.Inject;

public class DetailFragmentPresenterImpl implements IDetailFragmentPresenter {

    private IDetailFragmentView view;

    @Inject
    public DetailFragmentPresenterImpl() {
    }

    @Override
    public void init(IDetailFragmentView view) {
        this.view = view;
    }

    @Override
    public void onResume(long id) {
        sendRequest(id);
    }

    @Override
    public void onPause() {
    }

    private void sendRequest(long id) {
        getSingleMovieById(id);
        view.showProgressDialog();
    }

    private void getSingleMovieById(long id) {
        new getMovieFromDb().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id);
    }

    // ----- Inner Class for RequestListener

    public class getMovieFromDb extends AsyncTask<Long, Void, Movie> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Movie doInBackground(Long... params) {
            Long incId = params[0];
            return view.getMovie(incId);
        }

        @Override
        protected void onPostExecute(Movie result) {
            super.onPostExecute(result);
            view.hideProgressDialog();
            view.updateViews(result);
        }
    }
}
