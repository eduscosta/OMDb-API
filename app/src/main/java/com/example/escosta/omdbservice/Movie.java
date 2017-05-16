package com.example.escosta.omdbservice;

/**
 * Created by escosta on 15/05/2017.
 */

public class Movie {

    public final String title;
    public final String year;
    public final String runtime;
    public final String genre;
    public final String actors;
    public final String plot;
    public final String awards;

    public Movie(String title, String year, String runtime, String genre, String actors, String plot, String awards) {

        this.title = title;
        this.year = year;
        this.runtime = runtime;
        this.genre = genre;
        this.actors = actors;
        this.plot = plot;
        this.awards = awards;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getAwards() {
        return awards;
    }
}
