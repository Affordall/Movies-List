package com.testapps.movieslist.network.parser;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.testapps.movieslist.database.DatabaseKeyNames;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemJsonToModelConverter {

    public ItemJsonToModelConverter() {
    }

    private long getMovieIdResult(JSONObject jsonObjectItem) throws JSONException {
        return jsonObjectItem.getLong(DatabaseKeyNames.MOVIE_ID_STRING);
    }

    private int getMovieAdultFlagResult(JSONObject jsonObjectItem) throws JSONException {
        int resultFlag;
        if (jsonObjectItem.getBoolean(DatabaseKeyNames.MOVIE_ADULT_FLAG_STRING)) {
            resultFlag = 1;
        } else {
            resultFlag = 0;
        }
        return resultFlag;
    }

    private List<String> getMovieGenresListResult(JSONObject jsonObjectItem) throws JSONException {
        JSONArray jsonArrayGenres = null;
        if (jsonObjectItem.has(DatabaseKeyNames.MOVIE_GENRES_OBJECT_STRING)) {
            jsonArrayGenres = jsonObjectItem.getJSONArray(DatabaseKeyNames.MOVIE_GENRES_OBJECT_STRING);
        } else if (jsonObjectItem.has(DatabaseKeyNames.MOVIE_GENRES_ID_OBJECT_STRING)) {
            jsonArrayGenres = jsonObjectItem.getJSONArray(DatabaseKeyNames.MOVIE_GENRES_ID_OBJECT_STRING);
        }

        List<String> finalList = new ArrayList<>();

        for (int jsonArrayIndex = 0; jsonArrayIndex < jsonArrayGenres.length(); jsonArrayIndex++) {

            JSONObject jsonObjItem = jsonArrayGenres.optJSONObject(jsonArrayIndex);

            if (jsonObjItem != null && jsonObjItem.has("name")) {
                    String jsonFormattedString = jsonObjItem.optString("name" , "");
                    finalList.add(jsonFormattedString);
            }

        }

        return finalList;
    }

    private String getMovieLanguageResult(JSONObject jsonObjectItem) throws JSONException {
        return jsonObjectItem.getString(DatabaseKeyNames.MOVIE_LANGUAGE_STRING);
    }

    private String getMovieTitleResult(JSONObject jsonObjectItem) throws JSONException {
        return jsonObjectItem.getString(DatabaseKeyNames.MOVIE_TITLE_STRING);
    }

    private String getMovieOverviewResult(JSONObject jsonObjectItem) throws JSONException {
        return jsonObjectItem.getString(DatabaseKeyNames.MOVIE_OVERVIEW_STRING);
    }

    private String getMovieReleaseDateResult(JSONObject jsonObjectItem) throws JSONException {
        return jsonObjectItem.getString(DatabaseKeyNames.MOVIE_RELEASE_DATE_STRING);
    }

    private String getMoviePosterUrlResult(JSONObject jsonObjectItem) throws JSONException {
        return jsonObjectItem.getString(DatabaseKeyNames.MOVIE_POSTER_URL_STRING);
    }

    private double getMoviePopularityResult(JSONObject jsonObjectItem) throws JSONException {
        return jsonObjectItem.getDouble(DatabaseKeyNames.MOVIE_POPULARITY_STRING);
    }

    private String getMovieTrailerUrlResult(JSONObject jsonObjectItem) throws JSONException {
        return jsonObjectItem.getString(DatabaseKeyNames.MOVIE_TRAILER_URL_STRING);
    }

    private double getMovieVoteAverageResult(JSONObject jsonObjectItem) throws JSONException {
        return jsonObjectItem.getDouble(DatabaseKeyNames.MOVIE_VOTE_AVERAGE_STRING);
    }

    private long getMovieVoteCountResult(JSONObject jsonObjectItem) throws JSONException {
        return jsonObjectItem.getLong(DatabaseKeyNames.MOVIE_VOTE_COUNT_STRING);
    }

    /**
     * Interface callback to encapsulate class methods.
     * */

    private JsonToModelListener mCallbackJsonToModelConverter = new JsonToModelListener() {

        @Override
        public long getId(JSONObject jsonObjectItem) throws JSONException {
            return getMovieIdResult(jsonObjectItem);
        }

        @Override
        public int getAdultFlag(JSONObject jsonObjectItem) throws JSONException {
            return getMovieAdultFlagResult(jsonObjectItem);
        }

        @Override
        public List<?> getGenres(JSONObject jsonObjectItem) throws JSONException {
            return getMovieGenresListResult(jsonObjectItem);
        }

        @Override
        public String getLanguage(JSONObject jsonObjectItem) throws JSONException {
            return getMovieLanguageResult(jsonObjectItem);
        }

        @Override
        public String getTitle(JSONObject jsonObjectItem) throws JSONException {
            return getMovieTitleResult(jsonObjectItem);
        }

        @Override
        public String getOverview(JSONObject jsonObjectItem) throws JSONException {
            return getMovieOverviewResult(jsonObjectItem);
        }

        @Override
        public String getReleaseDate(JSONObject jsonObjectItem) throws JSONException {
            return getMovieReleaseDateResult(jsonObjectItem);
        }

        @Override
        public String getPosterUrl(JSONObject jsonObjectItem) throws JSONException {
            return getMoviePosterUrlResult(jsonObjectItem);
        }

        @Override
        public double getPopularity(JSONObject jsonObjectItem) throws JSONException {
            return getMoviePopularityResult(jsonObjectItem);
        }

        @Override
        public String getTrailerUrl(JSONObject jsonObjectItem) throws JSONException {
            return getMovieTrailerUrlResult(jsonObjectItem);
        }

        @Override
        public double getVoteAverage(JSONObject jsonObjectItem) throws JSONException {
            return getMovieVoteAverageResult(jsonObjectItem);
        }

        @Override
        public long getVoteCount(JSONObject jsonObjectItem) throws JSONException {
            return getMovieVoteCountResult(jsonObjectItem);
        }

    };

    public JsonToModelListener getmCallbackJsonToModelConverter() {
        return mCallbackJsonToModelConverter;
    }

}
