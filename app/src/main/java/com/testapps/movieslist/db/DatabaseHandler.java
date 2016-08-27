package com.testapps.movieslist.db;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.util.Log;

import com.testapps.movieslist.converters.InstanceModelGenerator;
import com.testapps.movieslist.models.Movie;
import com.testapps.movieslist.models.MovieBuilder;
import com.testapps.movieslist.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DatabaseHandler extends SQLiteOpenHelper implements MovieListener {

    private static final String DATABASE_NAME = "movies_db";
    private static final int DATABASE_VERSION = 1;

    private static final String ORDER_BY_DESCEND = " DESC";
    private static final String ORDER_BY_ASCEND = " ASC";

    // Tables name
    private static final String TABLE_MOVIES = "movies";

    private final ItemCursorToModelConverter toModelConverter = new ItemCursorToModelConverter();
    private CursorToModelListener c2m;

    private static final String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
            + DatabaseKeyNames.MOVIE_ID_STRING + " INTEGER,"
            + DatabaseKeyNames.MOVIE_ADULT_FLAG_STRING + " INTEGER,"
            + DatabaseKeyNames.MOVIE_GENRES_OBJECT_STRING + " TEXT,"
            + DatabaseKeyNames.MOVIE_LANGUAGE_STRING + " TEXT,"
            + DatabaseKeyNames.MOVIE_TITLE_STRING + " TEXT,"
            + DatabaseKeyNames.MOVIE_OVERVIEW_STRING + " TEXT,"
            + DatabaseKeyNames.MOVIE_RELEASE_DATE_STRING + " TEXT,"
            + DatabaseKeyNames.MOVIE_POSTER_URL_STRING + " TEXT,"
            + DatabaseKeyNames.MOVIE_POPULARITY_STRING + " TEXT,"
            + DatabaseKeyNames.MOVIE_TRAILER_URL_STRING + " TEXT,"
            + DatabaseKeyNames.MOVIE_VOTE_AVERAGE_STRING + " TEXT,"
            + DatabaseKeyNames.MOVIE_VOTE_COUNT_STRING + " TEXT" + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c2m = toModelConverter.getmCallbackCursorToModelConverter();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    public boolean isDBlocked() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.isDbLockedByCurrentThread();
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_MOVIES, null, null);
        } catch (SQLiteDatabaseLockedException e) {
            e.printStackTrace();
        } finally {
            closeDB(db);
        }
    }

    public Boolean doIt(String TableName, String ColumnName, Object ColumnData) {
        boolean outValue = false;
        try {
            outValue = doesRecordExist(TableName, ColumnName, ColumnData);
        } catch (SQLException e) {
            // It's fine if findUser throws a NPE
        }
        return outValue;
    }

    public Boolean doesRecordExist(String TableName, String ColumnName, Object ColumnData) throws SQLException {
        String q = "Select * FROM "+ TableName + " WHERE " + ColumnName + "='" + ColumnData + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q, null);
        try {
            return cursor.moveToFirst();
        } finally {
            cursor.close();
            closeDB(db);
        }
    }

    /**
     * All CRUD Operations
     * */

    @Override
    public void addMovieItem(Movie movieItem) {
        String sql = "INSERT OR REPLACE INTO " + TABLE_MOVIES +
                " ( " + DatabaseKeyNames.MOVIE_ID_STRING +
                ", " + DatabaseKeyNames.MOVIE_ADULT_FLAG_STRING +
                ", " + DatabaseKeyNames.MOVIE_GENRES_OBJECT_STRING +
                ", " + DatabaseKeyNames.MOVIE_LANGUAGE_STRING +
                ", " + DatabaseKeyNames.MOVIE_TITLE_STRING +
                ", " + DatabaseKeyNames.MOVIE_OVERVIEW_STRING +
                ", " + DatabaseKeyNames.MOVIE_RELEASE_DATE_STRING +
                ", " + DatabaseKeyNames.MOVIE_POSTER_URL_STRING +
                ", " + DatabaseKeyNames.MOVIE_POPULARITY_STRING +
                ", " + DatabaseKeyNames.MOVIE_TRAILER_URL_STRING +
                ", " + DatabaseKeyNames.MOVIE_VOTE_AVERAGE_STRING +
                ", " + DatabaseKeyNames.MOVIE_VOTE_COUNT_STRING +
                " ) " + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransactionNonExclusive();
        runSqlStatementDependingByApiVersion(db, sql, movieItem);
    }

    public boolean isMovieExist(long id) {
        return doIt(TABLE_MOVIES, DatabaseKeyNames.MOVIE_ID_STRING, id);
    }

    @Override
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> resultMovieList = InstanceModelGenerator.newInstanceMovieList();

        String selectQuery =  "SELECT  * FROM " + TABLE_MOVIES + " ORDER BY "+ DatabaseKeyNames.MOVIE_TITLE_STRING + ORDER_BY_ASCEND;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Movie item = createNewMovieByCursor(cursor);
                    resultMovieList.add(item);
                } while (cursor.moveToNext());
            }
        } catch (IllegalStateException e) {
            Utils.logError(e);
        } finally {
            cursor.close();
            closeDB(db);
        }
        return resultMovieList;
    }

    @Override
    public int getMoviesItemCount() {
        int num;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String QUERY = "SELECT  * FROM " + TABLE_MOVIES;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            cursor.close();
            closeDB(db);
            return num;
        } catch (Exception e) {
            Log.e("error", e + "");
        }
        return 0;
    }

    public Observable<ArrayList<Movie>> getRxMoviesList() {
        return makeObservable(getAllMoviesData())
        .subscribeOn(Schedulers.computation());
    }

    private Callable<ArrayList<Movie>> getAllMoviesData() {
        return this::getAllMovies;
    }

    private static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(func.call());
                        } catch(Exception ex) {
                            Log.e("--JAM-RSS-READER", "Error reading from the database", ex);
                        }
                    }
                });
    }

    private void runSqlStatementDependingByApiVersion(SQLiteDatabase db, String sql, Object incomeObj) {
        if (Utils.isKitkat()) {
            newApiSqlStatement(db, sql, incomeObj);
        } else {
            oldApiSqlStatement(db, sql, incomeObj);
        }
    }

    private void oldApiSqlStatement(SQLiteDatabase db, String sql, Object incomeObj) {
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            validateIncomingInstance(db, stmt, incomeObj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            stmt.close();
            closeDB(db);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void newApiSqlStatement(SQLiteDatabase db, String sql, Object incomeObj) {
        try (SQLiteStatement stmt = db.compileStatement(sql)) {
            validateIncomingInstance(db, stmt, incomeObj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            closeDB(db);
        }
    }

    private void validateIncomingInstance(SQLiteDatabase db, SQLiteStatement stmt, Object incomeObj) throws Exception {
        if (incomeObj instanceof Movie) {
            commonSqlStatementMovieItem(db, stmt, (Movie)incomeObj);
        } else {
            throw new Exception("Unknown incoming instance of class");
        }
    }

    private void commonSqlStatementMovieItem(SQLiteDatabase db, SQLiteStatement stmt, Movie movie) {
        try {
            safeStmt(stmt, 1, movie.getId());
            safeStmt(stmt, 2, movie.getAdult());
            safeStmt(stmt, 3, movie.getGenres().toString());
            safeStmt(stmt, 4, movie.getLanguage());
            safeStmt(stmt, 5, movie.getTitle());
            safeStmt(stmt, 6, movie.getOverview());
            safeStmt(stmt, 7, movie.getReleaseDate());
            safeStmt(stmt, 8, movie.getPosterUrl());
            safeStmt(stmt, 9, movie.getPopularity());
            safeStmt(stmt, 10, movie.getTrailerUrl());
            safeStmt(stmt, 11, movie.getVoteAverage());
            safeStmt(stmt, 12, movie.getVoteCount());
            stmt.execute();
            stmt.clearBindings();

            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    private Movie createNewMovieByCursor(Cursor cursor) {
        return new MovieBuilder()
                .setMovieId(c2m.getId(cursor))
                .setAdultFlag(c2m.getAdultFlag(cursor))
                .setGenresList(c2m.getGenres(cursor))
                .setMovieLanguage(c2m.getLanguage(cursor))
                .setMovieTitle(c2m.getTitle(cursor))
                .setOverview(c2m.getOverview(cursor))
                .setReleaseDate(c2m.getReleaseDate(cursor))
                .setPosterUrl(c2m.getPosterUrl(cursor))
                .setPopularity(c2m.getPopularity(cursor))
                .setTrailerUrl(c2m.getTrailerUrl(cursor))
                .setVoteAverage(c2m.getVoteAverage(cursor))
                .setVoteCount(c2m.getVoteCount(cursor))
                .build();
    }

    private void safeStmt(SQLiteStatement stmt, int index, String incString) {
        if (incString != null) {
            stmt.bindString(index, incString);
        } else {
            stmt.bindNull(index);
        }
    }

    private void safeStmt(SQLiteStatement stmt, int index, long incLong) {
        String referrer = Long.toString(incLong);
        if (referrer != null) {
            stmt.bindLong(index, incLong);
        } else {
            stmt.bindNull(index);
        }
    }

    private void safeStmt(SQLiteStatement stmt, int index, double incDouble) {
        String referrer = Double.toString(incDouble);
        if (referrer != null) {
            stmt.bindDouble(index, incDouble);
        } else {
            stmt.bindNull(index);
        }
    }

    public ArrayList<Movie> getSearchResult(String[] wordsForSearch) {

        ArrayList<Movie> itemList = InstanceModelGenerator.newInstanceMovieList();

        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES + " WHERE "
                + DatabaseKeyNames.MOVIE_TITLE_STRING + " LIKE " + "(cast(" + "%cns" +" as text))";

        StringBuilder cns = new StringBuilder();
        cns.append("'%$");
        for(int i = 0; i < wordsForSearch.length; i++) {
            cns.append(String.valueOf(wordsForSearch[i]));
            if (i < wordsForSearch.length - 1) {
                cns.append("%','%$");
            }
        }
        cns.append("%'");

        SQLiteDatabase db = this.getReadableDatabase();

        String finalQuery = null;
        try {
            finalQuery = selectQuery.replaceAll("%cns", cns.toString());
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        assert finalQuery != null;
        Cursor cursor = db.rawQuery(finalQuery, null);
        assert cursor != null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    Movie item = createNewMovieByCursor(cursor);
                    itemList.add(item);
                } while (cursor.moveToNext());
            }
        } catch (IllegalStateException e) {
            Utils.logError(e);
        } finally {
            closeDB(db);
        }
        return itemList;
    }

    private void closeDB(SQLiteDatabase db) {
        if (db != null && db.isOpen())
            db.close();
    }
}
