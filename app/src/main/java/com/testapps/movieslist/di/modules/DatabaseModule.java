package com.testapps.movieslist.di.modules;

import android.app.Application;
import android.content.Context;

import com.testapps.movieslist.database.DatabaseHandler;
import com.testapps.movieslist.network.parser.SaveToDbHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class DatabaseModule {

    @Provides
    @Singleton
    DatabaseHandler provideDatabaseHandler(Application application) {
        return new DatabaseHandler(application);
    }

    @Provides
    @Singleton
    SaveToDbHandler provideSaveToDbHandler(Application application) {
        return new SaveToDbHandler(application);
    }
}
