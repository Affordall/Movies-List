package com.testapps.movieslist.db;

import android.database.Cursor;

import java.util.List;

public interface CursorToModelListener {
    long getId(Cursor cursor);
    int getAdultFlag(Cursor cursor);
    List<?> getGenres(Cursor cursor);
    String getLanguage(Cursor cursor);
    String getTitle(Cursor cursor);
    String getOverview(Cursor cursor);
    String getReleaseDate(Cursor cursor);
    String getPosterUrl(Cursor cursor);
    double getPopularity(Cursor cursor);
    String getTrailerUrl(Cursor cursor);
    double getVoteAverage(Cursor cursor);
    long getVoteCount(Cursor cursor);
}
