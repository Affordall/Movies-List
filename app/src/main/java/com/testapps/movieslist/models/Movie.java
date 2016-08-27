package com.testapps.movieslist.models;

import java.util.List;

public class Movie {

    private long id;
    private int adult;
    private List<?> genres;
    private String language;
    private String title;
    private String overview;
    private String releaseDate;
    private String posterUrl;
    private double popularity;
    private String trailerUrl;
    private double voteAverage;
    private long voteCount;

    public Movie() {
    }

    public Movie(long id, int adult, List<?> genres, String language, String title, String overview, String releaseDate, String posterUrl, double popularity, String trailerUrl, double voteAverage, long voteCount) {
        this.id = id;
        this.adult = adult;
        this.genres = genres;
        this.language = language;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.popularity = popularity;
        this.trailerUrl = trailerUrl;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public long getId() {
        return id;
    }

    public int getAdult() {
        return adult;
    }

    public List<?> getGenres() {
        return genres;
    }

    public String getLanguage() {
        return language;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }
}
