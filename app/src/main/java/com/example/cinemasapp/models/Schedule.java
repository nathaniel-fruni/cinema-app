package com.example.cinemasapp.models;

public class Schedule {
    private long ID;
    private long MOVIE_ID;
    private long ROOM_ID;
    private String date;
    private String time;
    private String format;

    public Schedule (long ID, long MOVIE_ID, long ROOM_ID, String date, String time, String format) {
        this.ID = ID;
        this.MOVIE_ID = MOVIE_ID;
        this.ROOM_ID = ROOM_ID;
        this.date = date;
        this.time = time;
        this.format = format;
    }

    public long getID() { return ID; }
    public void setID(long ID) { this.ID = ID; }
    public long getMOVIE_ID() { return MOVIE_ID; }
    public void setMOVIE_ID(long MOVIE_ID) { this.MOVIE_ID = MOVIE_ID; }
    public long getROOM_ID() { return ROOM_ID; }
    public void setROOM_ID(long ROOM_ID) { this.ROOM_ID = ROOM_ID; }
    public String getDate() {  return date;}
    public void setDate(String date) { this.date = date; }
    public String getTime() {  return time;}
    public void setTime(String time) { this.time = time; }
    public String getFormat() {  return format;}
    public void setFormat(String format) { this.format = format; }

}
