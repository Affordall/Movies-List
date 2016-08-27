package com.testapps.movieslist.network.parser;

import android.content.Context;
import android.util.Log;

import com.testapps.movieslist.app.MoviesListApp;
import com.testapps.movieslist.converters.InstanceModelGenerator;
import com.testapps.movieslist.models.Movie;
import com.testapps.movieslist.models.MovieBuilder;
import com.testapps.movieslist.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class JsonParser {

    private static JsonParser sInstance;
    private static final String HTTP_SOURCE_URL = "https://gist.githubusercontent.com/numbata/5ed307d7953c3f7e716f/raw/b7887adc444188d8aa8e61d39b82950f28c03966/movies.json";
    private final ItemJsonToModelConverter toModelConverter = new ItemJsonToModelConverter();
    private JsonToModelListener j2m;
    private Request request;

    @Inject OkHttpClient client;
    @Inject SaveToDbHandler saveToDbHandler;

    public static synchronized JsonParser getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new JsonParser(context);
        }
        return sInstance;
    }

    private JsonParser(Context context) {
        MoviesListApp.get(context).getAppComponent().inject(this);
    }

    public ArrayList<Movie> getMoviesList() {
        j2m = toModelConverter.getmCallbackJsonToModelConverter();
        request = createRequest(HTTP_SOURCE_URL);
        String serverData = getServerData();
        return checkDataAndTryToGetJson(serverData);
    }

    private String getServerData() {
        return pullRequestToGetDataFromServer(client, request);
    }

    private Request createRequest(String url) {
        return new Request.Builder()
                .url(url)
                .build();
    }

    private String pullRequestToGetDataFromServer(OkHttpClient client, Request request) {
        String data = "";
        try {
            data = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            Utils.logError(e);
        }
        return data;
    }

    private ArrayList<Movie> checkDataAndTryToGetJson(String serverData) {
        if (serverData != null) {
            Log.e("--SERVER DATA--", " " + serverData);
            return parseJsonToList(serverData);
        } else {
            throw new RuntimeException("Server data is empty");
        }
    }

    private ArrayList<Movie> parseJsonToList(String serverData) {
        ArrayList<Movie> resultList = InstanceModelGenerator.newInstanceMovieList();
        try {
            JSONArray jsonArray = new JSONObject(serverData).getJSONArray("results");

            for (int jsonArrayIndex = 0; jsonArrayIndex < jsonArray.length(); jsonArrayIndex++) {
                JSONObject jsonObjItem = jsonArray.getJSONObject(jsonArrayIndex);

                Movie movieItem = new MovieBuilder()
                        .setMovieId(j2m.getId(jsonObjItem))
                        .setAdultFlag(j2m.getAdultFlag(jsonObjItem))
                        .setGenresList(j2m.getGenres(jsonObjItem))
                        .setMovieLanguage(j2m.getLanguage(jsonObjItem))
                        .setMovieTitle(j2m.getTitle(jsonObjItem))
                        .setOverview(j2m.getOverview(jsonObjItem))
                        .setReleaseDate(j2m.getReleaseDate(jsonObjItem))
                        .setPosterUrl(j2m.getPosterUrl(jsonObjItem))
                        .setPopularity(j2m.getPopularity(jsonObjItem))
                        .setTrailerUrl(j2m.getTrailerUrl(jsonObjItem))
                        .setVoteAverage(j2m.getVoteAverage(jsonObjItem))
                        .setVoteCount(j2m.getVoteCount(jsonObjItem))
                        .build();

                saveToDbHandler.saveMovies(movieItem);
                resultList.add(movieItem);
            }
        } catch (JSONException e) {
            Utils.logError(e);
        }
        return resultList;
    }
}
