package com.testapps.movieslist.di.components;

import com.testapps.movieslist.app.MoviesListApp;
import com.testapps.movieslist.di.modules.MoviesListAppModule;
import com.testapps.movieslist.network.parser.JsonParser;
import com.testapps.movieslist.network.parser.SaveToDbHandler;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                MoviesListAppModule.class
        })
public interface MoviesListAppComponent {
    void inject(MoviesListApp app);
    void inject(JsonParser jsonParser);
    void inject(SaveToDbHandler saveToDbHandler);
}
