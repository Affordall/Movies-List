package com.testapps.movieslist.database;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import java.util.Arrays;
import java.util.List;

public class ItemCursorToModelConverter {

    public ItemCursorToModelConverter() {
    }

    private long getMovieIdFromCursor(Cursor cursor) {
        return getLongCursor(cursor, DatabaseKeyNames.MOVIE_ID_STRING);
    }

    private int getMovieAdultFlagFromCursor(Cursor cursor) {
        return getIntCursor(cursor, DatabaseKeyNames.MOVIE_ADULT_FLAG_STRING);
    }

    private List<String> getGenresListFromCursor(Cursor cursor) {
        String genresString = getStringCursor(cursor, DatabaseKeyNames.MOVIE_GENRES_OBJECT_STRING);
        return Arrays.asList(genresString.split("\\s*,\\s*"));
    }

    private String getMovieLanguageFromCursor(Cursor cursor) {
        return getStringCursor(cursor, DatabaseKeyNames.MOVIE_LANGUAGE_STRING);
    }

    private String getMovieTitleFromCursor(Cursor cursor) {
        return getStringCursor(cursor, DatabaseKeyNames.MOVIE_TITLE_STRING);
    }

    private String getMovieOverviewFromCursor(Cursor cursor) {
        return getStringCursor(cursor, DatabaseKeyNames.MOVIE_OVERVIEW_STRING);
    }

    private String getMovieReleaseDateFromCursor(Cursor cursor) {
        return getStringCursor(cursor, DatabaseKeyNames.MOVIE_RELEASE_DATE_STRING);
    }

    private String getMoviePosterUrlFromCursor(Cursor cursor) {
        return getStringCursor(cursor, DatabaseKeyNames.MOVIE_POSTER_URL_STRING);
    }

    private double getMoviePopularityFromCursor(Cursor cursor) {
        return getDoubleCursor(cursor, DatabaseKeyNames.MOVIE_POPULARITY_STRING);
    }

    private String getMovieTrailerUtlFromCursor(Cursor cursor) {
        return getStringCursor(cursor, DatabaseKeyNames.MOVIE_TRAILER_URL_STRING);
    }

    private double getMovieVoteAverageFromCursor(Cursor cursor) {
        return getDoubleCursor(cursor, DatabaseKeyNames.MOVIE_VOTE_AVERAGE_STRING);
    }

    private long getMovieVoteCountFromCursor(Cursor cursor) {
        return getLongCursor(cursor, DatabaseKeyNames.MOVIE_VOTE_COUNT_STRING);
    }

    private String getStringCursor(Cursor cursor, String columnIndex) {
        return cursor.getString(cursor.getColumnIndex(columnIndex));
    }

    private int getIntCursor(Cursor cursor, String columnIndex) throws CursorIndexOutOfBoundsException {
        return cursor.getInt(cursor.getColumnIndex(columnIndex));
    }

    private long getLongCursor(Cursor cursor, String columnIndex) throws CursorIndexOutOfBoundsException {
        return cursor.getLong(cursor.getColumnIndex(columnIndex));
    }

    private double getDoubleCursor(Cursor cursor, String columnIndex) throws CursorIndexOutOfBoundsException {
        return cursor.getDouble(cursor.getColumnIndex(columnIndex));
    }

    /**
     * Interface callback to encapsulate class methods.
     * */

    private CursorToModelListener mCallbackCursorToModelConverter = new CursorToModelListener() {

        @Override
        public long getId(Cursor cursor) {
            return getMovieIdFromCursor(cursor);
        }

        @Override
        public int getAdultFlag(Cursor cursor) {
            return getMovieAdultFlagFromCursor(cursor);
        }

        @Override
        public List<?> getGenres(Cursor cursor) {
            return getGenresListFromCursor(cursor);
        }

        @Override
        public String getLanguage(Cursor cursor) {
            return getMovieLanguageFromCursor(cursor);
        }

        @Override
        public String getTitle(Cursor cursor) {
            return getMovieTitleFromCursor(cursor);
        }

        @Override
        public String getOverview(Cursor cursor) {
            return getMovieOverviewFromCursor(cursor);
        }

        @Override
        public String getReleaseDate(Cursor cursor) {
            return getMovieReleaseDateFromCursor(cursor);
        }

        @Override
        public String getPosterUrl(Cursor cursor) {
            return getMoviePosterUrlFromCursor(cursor);
        }

        @Override
        public double getPopularity(Cursor cursor) {
            return getMoviePopularityFromCursor(cursor);
        }

        @Override
        public String getTrailerUrl(Cursor cursor) {
            return getMovieTrailerUtlFromCursor(cursor);
        }

        @Override
        public double getVoteAverage(Cursor cursor) {
            return getMovieVoteAverageFromCursor(cursor);
        }

        @Override
        public long getVoteCount(Cursor cursor) {
            return getMovieVoteCountFromCursor(cursor);
        }
    };

    public CursorToModelListener getmCallbackCursorToModelConverter() {
        return mCallbackCursorToModelConverter;
    }

}
