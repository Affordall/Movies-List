package com.testapps.movieslist.di.modules;


import com.testapps.movieslist.adapter.MovieAdapter;
import com.testapps.movieslist.presenters.DetailFragmentPresenterImpl;
import com.testapps.movieslist.presenters.ListFragmentPresenterImpl;
import com.testapps.movieslist.presenters.MainActivityPresenterImpl;
import com.testapps.movieslist.views.IMainActivityView;

import dagger.Module;
import dagger.Provides;


@Module
public class MainActivityModule {

    private IMainActivityView view;

    public MainActivityModule(IMainActivityView view) {
        this.view = view;
    }

    @Provides
    public IMainActivityView provideView() {
        return view;
    }

    @Provides
    public MainActivityPresenterImpl provideMainActivityPresenterImpl (IMainActivityView view){
        return  new MainActivityPresenterImpl(view);
    }

    @Provides
    public ListFragmentPresenterImpl provideListFragmentPresenterImpl() {
        return new ListFragmentPresenterImpl();
    }

    @Provides
    public DetailFragmentPresenterImpl provideDetailFragmentPresenterImpl() {
        return new DetailFragmentPresenterImpl();
    }
}
