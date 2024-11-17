package com.example.cinemasapp.models;

public class Movie {
    private long ID;
    private String title;
    private String picture;
    private String director;
    private long release_year;
    private String description;
    private String genre;
    private String language;
    private long length_in_min;
    private long min_age;

    public Movie(long ID, String title, String picture, String director, long release_year, String description, String genre, String language, long length_in_min, long min_age) {
        this.ID = ID;
        this.title = title;
        this.picture = picture;
        this.director = director;
        this.release_year = release_year;
        this.description = description;
        this.genre = genre;
        this.language = language;
        this.length_in_min = length_in_min;
        this.min_age = min_age;
    }

    public long getID() { return ID; }
    public void setID(long ID) { this.ID = ID; }
    public String getTitle() {  return title;}
    public void setTitle(String title) { this.title = title; }
    public String getPicture() {  return picture;}
    public void setPicture(String picture) { this.picture = picture; }
    public String getDirector() {  return director;}
    public void setDirector(String director) { this.director = director; }
    public long getReleaseYear() { return release_year; }
    public void setReleaseYear(long release_year) { this.release_year = release_year; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getLanguage() {  return language;}
    public void setLanguage(String language) { this.language = language; }
    public long getLength() { return length_in_min; }
    public void setLength(long length_in_min) { this.length_in_min = length_in_min; }
    public long getMinAge() { return min_age; }
    public void setMinAge(long min_age) { this.min_age = min_age; }

}
