package com.testapps.movieslist.presenters;

import com.testapps.movieslist.common.BaseFragmentPresenter;
import com.testapps.movieslist.views.IDetailFragmentView;

public interface IDetailFragmentPresenter extends BaseFragmentPresenter<IDetailFragmentView> {
    void onResume(long id);
    void onPause();
}
