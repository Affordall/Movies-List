package com.testapps.movieslist.di.components;

import android.content.Context;

import com.testapps.movieslist.app.MoviesListApp;
import com.testapps.movieslist.di.modules.MoviesListAppModule;
import com.testapps.movieslist.network.parser.JsonParser;
import com.testapps.movieslist.network.parser.SaveToDbHandler;
import com.testapps.movieslist.presenters.ListFragmentPresenterImpl;
import com.testapps.movieslist.views.DetailFragment;
import com.testapps.movieslist.views.ListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                MoviesListAppModule.class
        })
public interface MoviesListAppComponent {

    Context context();

    void inject(MoviesListApp app);
    void inject(JsonParser jsonParser);
    void inject(SaveToDbHandler saveToDbHandler);
    void inject(DetailFragment detailFragment);
    void inject(ListFragmentPresenterImpl listFragmentPresenter);
}
