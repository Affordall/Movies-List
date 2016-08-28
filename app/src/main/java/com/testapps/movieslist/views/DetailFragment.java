package com.testapps.movieslist.views;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.squareup.picasso.Picasso;
import com.testapps.movieslist.R;
import com.testapps.movieslist.common.BaseFragment;
import com.testapps.movieslist.database.DatabaseHandler;
import com.testapps.movieslist.di.components.IMainActivityComponent;
import com.testapps.movieslist.models.Movie;
import com.testapps.movieslist.presenters.DetailFragmentPresenterImpl;
import com.testapps.movieslist.utils.Utils;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class DetailFragment extends BaseFragment implements IDetailFragmentView {

    @Inject DetailFragmentPresenterImpl presenter;
    @Inject DatabaseHandler handler;

    @BindView(R.id.iv_poster_url) ImageView mPoster;
    @Nullable @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.tv_title) TextView mTitle;
    @BindView(R.id.tv_genres) TextView mGenres;
    @BindView(R.id.tv_overview) TextView mOverview;
    @BindView(R.id.tv_adult) TextView mAdult;
    @BindView(R.id.tv_language) TextView mLanguage;
    @BindView(R.id.tv_release_date) TextView mReleaseDate;
    @BindView(R.id.tv_popularity) TextView mPopularity;
    @BindView(R.id.tv_vote_average) TextView mVoteAverage;
    @BindView(R.id.tv_vote_count) TextView mVoteCount;
    @BindView(R.id.vv_trailer) JCVideoPlayerStandard mTrailer;

    private SpotsDialog dialog;

    public static final String BUNDLE_ID = "bundleID";
    private long singleId;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(long id) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(BUNDLE_ID, id);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    // ----- Lifecycle override method

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null && getArguments().containsKey(BUNDLE_ID)) {
            this.singleId = getArguments().getLong(BUNDLE_ID);
        } else {
            throw new IllegalArgumentException("Must be created through newInstance(long id)");
        }
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
        presenter.onResume(singleId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment_detail = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, fragment_detail);
        return fragment_detail;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }

    // -----  IDetailFragmentView implement method


    @Override
    public void updateViews(Movie result) {
        Picasso.with(getActivity())
                .load(result.getPosterUrl())
                .error(R.color.gray_overlay)
                .placeholder(R.color.gray_overlay)
                .into(mPoster);
        mTitle.setText(result.getTitle());

        String[] genre_names = Stream.of(result.getGenres()).toArray(String[]::new);
        mGenres.setText(Arrays.toString(genre_names).replaceAll("\\[|\\]", ""));
        if (result.getAdult() == 0) {
            mAdult.setText("For everyone");
        } else {
            mAdult.setText("Adult film");
        }
        mLanguage.setText(result.getLanguage());
        mOverview.setText(result.getOverview());
        mPopularity.setText(String.valueOf(result.getPopularity()));
        mReleaseDate.setText(result.getReleaseDate());
        mVoteAverage.setText(String.valueOf(result.getVoteAverage()));
        mVoteCount.setText(String.valueOf(result.getVoteCount()));

        mTrailer.setUp(result.getTrailerUrl(), JCVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, result.getTitle());

        fab.setOnClickListener(view -> Utils.shareIt(result.getTitle()));
    }

    @Override
    public void showProgressDialog() {
        dialog = new SpotsDialog(getActivity(), R.style.CustomProgressDialogStyle);
        dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        dialog.dismiss();
    }

    @Override
    public Movie getMovie(long id) {
        return handler.getMovie(id);
    }

}
