package model;

import java.sql.Date;

public class Game {
    private int gID;
    private int btID;
    private int rtID;
    private Date day;
    private int aID;
    private int year;
    private String season;

    public Game(int gID, int btID, int rtID, Date day, int aID, String season, int year) {
        this.gID = gID;
        this.btID = btID;
        this.rtID = rtID;
        this.day = day;
        this.aID = aID;
        this.season = season;
        this.year = year;
    }

    public int getgID() {
        return gID;
    }

    public int getBtID() {
        return btID;
    }

    public int getRtID() {
        return rtID;
    }

    public Date getDay() {
        return day;
    }

    public int getaID() {
        return aID;
    }

    public String getSeason() {
        return season;
    }

    public String getDescription() {
        String desc = gID + ": " + season + " " + year + " " + rtID + " vs. " + btID;
        return desc;
    }
}
