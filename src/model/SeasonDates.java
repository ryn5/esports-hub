package model;

public class SeasonDates {
    private final String date;
    private final String season;

    public SeasonDates(String date, String season) {
        this.date = date;
        this.season = season;
    }

    public String getDate() {
        return date;
    }

    public String getSeason() {
        return season;
    }
}
