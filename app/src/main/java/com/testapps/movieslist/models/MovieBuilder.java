package com.testapps.movieslist.models;

import java.util.List;

public class MovieBuilder {

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

    public MovieBuilder() {
    }

    public MovieBuilder setMovieId(long id) {
        this.id = id;
        return this;
    }

    public MovieBuilder setAdultFlag(int adult) {
        this.adult = adult;
        return this;
    }

    public MovieBuilder setGenresList(List<?> genresList) {
        this.genres = genresList;
        return this;
    }

    public MovieBuilder setMovieLanguage(String movieLanguage) {
        this.language = movieLanguage;
        return this;
    }

    public MovieBuilder setMovieTitle(String movieTitle) {
        this.title = movieTitle;
        return this;
    }

    public MovieBuilder setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public MovieBuilder setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public MovieBuilder setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
        return this;
    }

    public MovieBuilder setPopularity(double popularity) {
        this.popularity = popularity;
        return this;
    }

    public MovieBuilder setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
        return this;
    }

    public MovieBuilder setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    public MovieBuilder setVoteCount(long voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    public Movie build() {
        this.validate();
        return new Movie(
                id,
                adult,
                genres,
                language,
                title,
                overview,
                releaseDate,
                posterUrl,
                popularity,
                trailerUrl,
                voteAverage,
                voteCount
        );
    }

    private void validate() {
        if (title == null) {
            throw new IllegalStateException("Title is null");
        }
    }
}
