package com.testapps.movieslist.converters;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

/**
 * Created by affy on 08.08.16.
 */
public abstract class InstanceModelGenerator {

    @Contract(" -> !null")
    public static synchronized <Movie>ArrayList<Movie> newInstanceMovieList() {
        return new ArrayList<>();
    }
}
