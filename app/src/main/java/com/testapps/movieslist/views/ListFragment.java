package com.testapps.movieslist.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.testapps.movieslist.R;
import com.testapps.movieslist.adapter.MovieAdapter;
import com.testapps.movieslist.common.BaseFragment;
import com.testapps.movieslist.converters.InstanceModelGenerator;
import com.testapps.movieslist.database.DatabaseHandler;
import com.testapps.movieslist.di.components.IMainActivityComponent;
import com.testapps.movieslist.models.Movie;
import com.testapps.movieslist.presenters.ListFragmentPresenterImpl;
import com.testapps.movieslist.utils.Utils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends BaseFragment implements IListFragmentView {

    @Inject ListFragmentPresenterImpl presenter;
    @Inject DatabaseHandler handler;

    @BindView(R.id.swipe_container) SwipeRefreshLayout swipeLayout;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    private MovieAdapter mMovieAdapter;
    private View rootView;

    public ListFragment() {
    }

    // ----- Lifecycle override method

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
        updateUI();
        //presenter.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_list, container, false);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredLayoutManager.setGapStrategy(
                StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        swipeLayout.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);

        MovieAdapter.OnItemClickListener onItemClickListener =
                (view1, position) -> presenter.onItemClick(mMovieAdapter.getMovieData(position));

        mMovieAdapter = new MovieAdapter(getContext(), InstanceModelGenerator.newInstanceMovieList(), onItemClickListener);
        mRecyclerView.addOnItemTouchListener(mMovieAdapter);
        mRecyclerView.setAdapter(mMovieAdapter);

        swipeLayout.setOnRefreshListener(this::checkInternetAndStartService);

    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }

    // -----  IListFragmentView implement method

    @Override
    public void setMoviesListAdapter(ArrayList<Movie> itemMoviesList) {
            presenter.addListToAdapter(itemMoviesList);
    }

    @Override
    public void addListToAdapter(ArrayList<Movie> itemMoviesList) {
        mMovieAdapter.refresh(itemMoviesList);
    }

    @Override
    public void getMoviesItems() {
        presenter.onParse();
    }

    @Override
    public void showProgressDialog() {
        isVisibleProgress(true);
    }

    @Override
    public void hideProgressDialog() {
        isVisibleProgress(false);
    }

    private void isVisibleProgress(boolean pb) {
        if (pb) {
            swipeLayout.setRefreshing(true);
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        } else {
            swipeLayout.setRefreshing(false);
            mProgressBar.setVisibility(ProgressBar.GONE);
        }
    }

    @Override
    public void replaceToDetailFragment(long id) {
        DetailFragment detailFragment = DetailFragment.newInstance(id);
        if (Utils.isTwoPane(getActivity())) {
            replaceSingleFragmentInOtherThread(detailFragment);
        } else {
            Utils.setFragment(getContext(), detailFragment, true, "frag_single");
        }
    }

    private void replaceSingleFragment(Fragment infrag) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_single_container, infrag, "single") //"single"
                .commit();
    }

    private void replaceSingleFragmentInOtherThread(final Fragment fragWithData) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                replaceSingleFragment(fragWithData);
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void updateUI() {
        if (isDbEmpty()) {
            checkInternetAndStartService();
        } else {
            getProductsAndRefreshAdapter();
        }
    }

    private boolean isDbEmpty() {
        return handler.getMoviesItemCount() == 0;
    }

    private void checkInternetAndStartService() {
        if (Utils.isNetworkAvailable()) {
            if (!isDbEmpty()) {
                handler.deleteAll();
            }
            getMoviesItems();
        } else {
            Toast.makeText(getContext(), getString(R.string.internet_is_off), Toast.LENGTH_SHORT).show();
        }
    }

    private void getProductsAndRefreshAdapter() {
        mMovieAdapter.refresh(handler.getAllMovies());
    }
}
