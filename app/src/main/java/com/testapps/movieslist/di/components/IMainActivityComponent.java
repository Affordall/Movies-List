package com.testapps.movieslist.di.components;


import com.testapps.movieslist.di.ActivityScope;
import com.testapps.movieslist.di.modules.DatabaseModule;
import com.testapps.movieslist.di.modules.MainActivityModule;
import com.testapps.movieslist.views.DetailFragment;
import com.testapps.movieslist.views.ListFragment;
import com.testapps.movieslist.views.MainActivity;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = MoviesListAppComponent.class,
        modules = MainActivityModule.class
)
public interface IMainActivityComponent {
    void inject(MainActivity activity);
    void inject(ListFragment listFragment);
    void inject(DetailFragment detailFragment);
}
