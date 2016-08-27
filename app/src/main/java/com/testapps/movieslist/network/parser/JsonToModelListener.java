package com.testapps.movieslist.network.parser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface JsonToModelListener {
    long getId(JSONObject jsonObjectItem) throws JSONException;
    int getAdultFlag(JSONObject jsonObjectItem) throws JSONException;
    List<?> getGenres(JSONObject jsonObjectItem) throws JSONException;
    String getLanguage(JSONObject jsonObjectItem) throws JSONException;
    String getTitle(JSONObject jsonObjectItem) throws JSONException;
    String getOverview(JSONObject jsonObjectItem) throws JSONException;
    String getReleaseDate(JSONObject jsonObjectItem) throws JSONException;
    String getPosterUrl(JSONObject jsonObjectItem) throws JSONException;
    double getPopularity(JSONObject jsonObjectItem) throws JSONException;
    String getTrailerUrl(JSONObject jsonObjectItem) throws JSONException;
    double getVoteAverage(JSONObject jsonObjectItem) throws JSONException;
    long getVoteCount(JSONObject jsonObjectItem) throws JSONException;
}
